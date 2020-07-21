
<template>
  <div class="app-container">

    <el-input
      v-model="filterText"
      placeholder="输入关键字进行过滤"/>

    <el-tree
      ref="tree"
      :data="subjectList"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all/>
  </div>
</template>
<script>
import subject from '@/api/edu/subject'

export default {
  data() {
    return {
      filterText: '', // 过滤文本
      subjectList: [], // 课程列表
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },

  watch: {
    // filterText(new, old)
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },

  created() {
    this.fetchNodeList()
  },

  methods: {

    fetchNodeList() {
      subject.getNestedTreeList().then(response => {
        console.log(response)
        if (response.success === true) {
          this.subjectList = response.data.items
        }
      })
    },

    // 节点过滤的方法
    filterNode(value, data) {
    //   console.log('filterNode:第一个参数' + value)
    //   console.log('filterNode:第二个参数')
      console.log(data)
      //   console.log('执行filterNode.....')
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    }
  }
}
</script>
