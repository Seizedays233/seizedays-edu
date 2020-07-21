<template>
  <!-- 添加和修改课时表单 -->
  <el-dialog :visible.sync="dialogVisible" title="添加课时" @close="close">
    <el-form :model="video" label-width="120px">
      <el-form-item label="课时标题">
        <el-input v-model="video.title"/>
      </el-form-item>
      <el-form-item label="课时排序">
        <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
      </el-form-item>
      <el-form-item label="是否免费">
        <el-radio-group v-model="video.free">
          <el-radio :label="true">免费</el-radio>
          <el-radio :label="false">默认</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="上传视频">

        <el-upload
          :on-success="handleVodUploadSuccess"
          :on-exceed="handleUploadExceed"
          :before-remove="beforeVodRemove"
          :on-remove="handleVodRemove"
          :file-list="fileList"
          :action="BASE_API+'/admin/vod/video/upload'"
          :limit="1"
          class="upload-demo">
          <el-button size="small" type="primary">上传视频</el-button>
          <el-tooltip placement="right-end">
            <div slot="content">最大支持1G，<br>
              支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
              GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
              MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
              SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
            <i class="el-icon-question"/>
          </el-tooltip>
        </el-upload>

      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import video from '@/api/edu/video'
import vod from '@/api/edu/vod'

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
      video: {// 课时对象
        chapterId: '',
        title: '',
        sort: 0,
        free: false,
        videoSourceId: '',
        videoOriginalName: ''
      },

      fileList: [], // 文件上传列表
      BASE_API: process.env.BASE_API, // 接口api地址

      timeout: '',
      partSize: '',
      parallel: '',
      retryCount: '',
      retryDuration: '',
      region: 'cn-shanghai',
      userId: '1506273167771201',
      file: null,
      authProgress: null,
      uploadDisabled: true,
      uploader: null,
      statusText: ''
    }
  },

  methods: {
    open(chapterId, videoId) {
      console.log('open')
      console.log(this.video)
      this.dialogVisible = true
      this.video.chapterId = chapterId
      if (videoId) {
        video.getById(videoId).then(response => {
          this.video = response.data.item
          if (this.video.videoSourceId && this.video.videoOriginalName) {
            this.fileList = [{ 'name': this.video.videoOriginalName }]
          }
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
      if (!this.video.id) {
        this.save()
      } else {
        this.update()
      }
    },

    save() {
      this.video.courseId = this.courseId
      video.save(this.video).then(response => {
        this.$message({
          type: 'success',
          message: '保存成功!'
        })
        // 关闭组件
        this.close()
        // 调用父组件监听函数
        this.$emit('onSaveSuccess')
      })
    },

    update() {
      video.updateById(this.video).then(response => {
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
      console.log('resetForm')
      this.video.id = null
      this.video.title = ''
      this.video.sort = 0
      this.video.chapterId = ''
      this.video.free = false
      this.video.videoSourceId = ''

      this.fileList = []
    },

    // 视频上传成功的回调
    handleVodUploadSuccess(response, file, fileList) {
      this.video.videoSourceId = response.data.videoId
      this.video.videoOriginalName = file.name
    },

    // 文件超出上传限制的回调
    handleUploadExceed() {
      this.$message.warning('最多只能上传一个视频文件，如果想重新上传视频，请先删除原有视频文件')
    },

    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },

    // 删除时回调
    handleVodRemove() {
      vod.removeById(this.video.videoSourceId).then(response => {
        this.video.videoSourceId = ''
        this.video.videoOriginalName = ''
        this.fileList = []
        video.updateById(this.video)

        this.$message({
          type: 'success',
          message: response.message
        })
      })
    }
  }
}
</script>
