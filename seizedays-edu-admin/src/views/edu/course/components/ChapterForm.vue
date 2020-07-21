<template>
  <!-- 添加和修改章节表单 -->
  <el-dialog :visible.sync="dialogVisible" title="添加章节" @close="close">
    <el-form :model="chapter" label-width="120px">
      <el-form-item label="章节标题">
        <el-input v-model="chapter.title"/>
      </el-form-item>
      <el-form-item label="章节排序">
        <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import chapter from '@/api/edu/chapter'

export default {

  // 父组件向子组件传值
  props: {
    courseId: {
      type: String,
      default: null
    }
  },

  data() {
    return {
      dialogVisible: false,
      chapter: {// 章节对象
        courseId: '',
        title: '',
        sort: 0
      }
    }
  },

  methods: {
    open(chapterId) {
      this.dialogVisible = true
      if (chapterId) {
        chapter.getById(chapterId).then(response => {
          this.chapter = response.data.item
        })
      }
    },

    close() {
      console.log('close')
      this.dialogVisible = false
      // 重置表单
      this.resetForm()
    },

    saveOrUpdate() {
      if (!this.chapter.id) {
        this.save()
      } else {
        this.update()
      }
    },

    save() {
      console.log(this.chapter)
      this.chapter.courseId = this.courseId
      chapter.save(this.chapter).then(response => {
        this.$message({
          type: 'success',
          message: '保存成功!'
        })
        // 关闭组件
        this.close()
        // 调用父组件监听函数：定义监听函数
        this.$emit('onSaveSuccess')
      })
    },

    update() {
      chapter.updateById(this.chapter).then(response => {
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
        // 关闭组件
        this.close()
        // 调用父组件监听函数
        this.$emit('onSaveSuccess')
      })
    },

    resetForm() {
      this.chapter.title = ''
      this.chapter.sort = 0
    }
  }
}
</script>
