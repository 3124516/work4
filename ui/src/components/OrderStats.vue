<template>
<div class="stats-container">
  <el-card>
    <h2>实时订单统计</h2>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-statistic title="今日订单数" :value="orderCount" />
      </el-col>
      <el-col :span="8">
        <el-statistic title="总数量" :value="totalQuantity" />
      </el-col>
      <el-col :span="8">
        <el-statistic title="总金额" :value="totalAmount" :precision="2" />
      </el-col>
    </el-row>

    <el-table :data="recentOrders" style="width: 100%; margin-top: 20px">
      <el-table-column prop="productName" label="商品" />
      <el-table-column prop="quantity" label="数量" width="100" />
      <el-table-column prop="amount" label="金额" width="120" />
      <el-table-column label="时间" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.timestamp) }}
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</div>
</template>

<script>
export default {
data() {
  return {
    orderCount: 0,
    totalQuantity: 0,
    totalAmount: 0,
    recentOrders: []
  }
},
methods: {
  formatDate(timestamp) {
    return new Date(timestamp).toLocaleTimeString()
  },
  addOrder(order) {
    this.orderCount++
    this.totalQuantity += order.quantity
    this.totalAmount += order.amount

    // 添加时间戳用于显示
    order.timestamp = new Date().getTime()

    this.recentOrders.unshift(order)
    if (this.recentOrders.length > 5) {
      this.recentOrders.pop()
    }
  }
}
}
</script>

<style scoped>
.stats-container {
padding: 20px;
}
</style>