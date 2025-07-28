<template>
<div class="app-container">
  <el-row :gutter="20">
    <el-col :span="12">
      <el-card class="order-form">
        <h2>提交新订单</h2>
        <el-form :model="form" label-width="80px">
          <el-form-item label="商品名称">
            <el-input v-model="form.productName" />
          </el-form-item>
          <el-form-item label="数量">
            <el-input-number v-model="form.quantity" :min="1" />
          </el-form-item>
          <el-form-item label="金额">
            <el-input-number v-model="form.amount" :min="0" :precision="2" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitOrder">提交订单</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
    <el-col :span="12">
      <OrderStats ref="stats" />
    </el-col>
  </el-row>
</div>
</template>

<script>
import OrderStats from '@/components/OrderStats.vue'
import axios from 'axios'
import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

export default {
components: { OrderStats },
data() {
  return {
    form: {
      productName: '',
      quantity: 1,
      amount: 100
    },
    stompClient: null
  }
},
mounted() {
  this.connectWebSocket()
},
methods: {
  submitOrder() {
  // 记录提交的数据
  console.log("提交订单数据:", this.form)
  
  axios.post('http://localhost:8081/orders', this.form)
    .then(response => {
      console.log("订单提交成功:", response.data)
      this.$message.success('订单提交成功！')
      this.form.productName = ''
    })
    .catch(err => {
      // 更详细的错误信息
      let errorMessage = '订单提交失败'
      
      if (err.response) {
        // 服务器返回了响应
        console.error('请求成功但服务器返回错误状态:', err.response.status)
        console.error('错误详情:', err.response.data)
        errorMessage = `服务器错误: ${err.response.status}`
        
        if (err.response.data) {
          errorMessage += ` - ${err.response.data}`
        }
      } else if (err.request) {
        // 请求已发出但没有收到响应
        console.error('未收到服务器响应:', err.request)
        errorMessage = '无法连接到服务器'
      } else {
        // 在设置请求时出了问题
        console.error('请求设置错误:', err.message)
        errorMessage = `请求配置错误: ${err.message}`
      }
      
      this.$message.error(errorMessage)
    })
},

  connectWebSocket() {
  const socket = new SockJS('http://localhost:8081/ws')
  this.stompClient = Stomp.over(socket)
  
  // 启用调试模式以便查看连接日志
  this.stompClient.debug = str => console.log('STOMP: ' + str)
  
  this.stompClient.connect({}, (frame) => {
    console.log('Connected: ' + frame)
    
    this.stompClient.subscribe('/topic/orders', (message) => {
      const order = JSON.parse(message.body)
      this.$refs.stats.addOrder(order)
    })
  }, (error) => {
    console.error('WebSocket连接错误:', error)
    // 3秒后重试
    setTimeout(() => this.connectWebSocket(), 3000)
  })
}
}
}
</script>

<style>
.app-container {
padding: 20px;
font-family: 'Avenir', Helvetica, Arial, sans-serif;
}
.order-form {
height: calc(100vh - 40px);
}
</style>