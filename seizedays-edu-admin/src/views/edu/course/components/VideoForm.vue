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
          ref="upload"
          :on-change="fileChange"
          :on-exceed="handleUploadExceed"
          :before-remove="beforeVodRemove"
          :on-remove="handleVodRemove"
          :file-list="fileList"
          :limit="1"
          :auto-upload="false"
          class="upload-demo"
          action="">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-tooltip placement="right-end">
            <div slot="content">最大支持1G，<br>
              支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
              GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
              MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
              SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
            <i class="el-icon-question"/>
          </el-tooltip>
          <el-button
            :disabled="uploadDisabled"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="authUpload">开始上传</el-button>
          <label class="status"><span>{{ statusText }}</span></label>
          <span class="progress"><i id="auth-progress">{{ authProgress }}</i></span>
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
      userId: '您的账号id', // ali云账号 id
      file: null, // 被上传文件
      authProgress: null, // 上传进度
      uploadDisabled: true, // 上传组件是否可用
      uploader: null, // 关键对象：核心上传组件
      statusText: '' // 状态字符串
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

      this.file = null
      this.authProgress = null
      this.uploadDisabled = true
      this.uploader = null
      this.statusText = ''
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
    },

    // 选取视频文件
    fileChange(file, fileList) {
      console.log(file)
      console.log(fileList)
      this.file = file.raw // 获取被上传的文件的元数据信息
      if (!this.file) {
        this.$alert('请先选择需要上传的文件!', '提示')
        return
      }

      // 当再次点击“选择文件”时，停止之前的上传，初始化上传进度和上传文本的状态
      if (this.uploader) {
        this.uploader.stopUpload()
        this.authProgress = 0
        this.statusText = ''
      }

      // 初始化上传组件
      const userData = '{"Vod":{}}'
      this.uploader = this.createUploader()
      this.uploader.addFile(this.file, null, null, null, userData)

      // this.uploadDisabled = false // 选择文件按钮可用
    },

    // 上传视频文件，携带播放凭证
    authUpload() {
      if (this.uploader !== null) {
        // 执行上传
        this.uploader.startUpload()
        this.uploadDisabled = true // 选择文件按钮不可用
      }
    },

    // 创建视频上传组件
    createUploader() {
      const self = this

      /* eslint-disable no-undef */
      const uploader = new AliyunUpload.Vod({
        userId: self.userId, // 阿里账号ID
        region: self.region, // 上传到点播的地域， 默认值为'cn-shanghai',//eu-central-1,ap-southeast-1
        timeout: self.timeout || 60000, // 超时时间
        partSize: self.partSize || 1048576, // 分片大小默认1M，不能小于100K
        parallel: self.parallel || 5, // 并行上传分片个数，默认5
        retryCount: self.retryCount || 3, // 网络原因失败时，重新上传次数，默认为3
        retryDuration: self.retryDuration || 2, // 网络原因失败时，重新上传间隔时间，默认为2秒

        // 添加文件成功
        'addFileSuccess': function(uploadInfo) {
          console.log('addFileSuccess...')
          console.log(uploadInfo)
          self.uploadDisabled = false
          self.statusText = '添加文件成功, 等待上传...'
          console.log('addFileSuccess: ' + uploadInfo.file.name)
        },

        // 开始上传：获取上传凭证等信息并设置给文件上传对象
        'onUploadstarted': function(uploadInfo) {
          console.log('onUploadstarted...')
          console.log(uploadInfo)
          console.log('获取上传地址和凭证')
          const fileName = uploadInfo.file.name
          const title = fileName.substring(0, fileName.lastIndexOf('.'))
          // 调用 获取视频上传地址和凭证接口
          // 调用api： uploader.setUploadAuthAndAddress 方法
          vod.getUploadAuthAndAddress(title, fileName).then(response => {
            const uploadAuth = response.data.response.uploadAuth
            const uploadAddress = response.data.response.uploadAddress
            const videoId = response.data.response.videoId

            // 为文件上传对象设置上传凭证、上传地址、和videoId的值
            uploader.setUploadAuthAndAddress(uploadInfo, uploadAuth, uploadAddress, videoId)
          })
          self.statusText = '文件开始上传...'
        },

        // 文件上传成功
        'onUploadSucceed': function(uploadInfo) {
          console.log('onUploadSucceed...')
          console.log(uploadInfo.file)

          self.statusText = '文件上传成功!'

          const fileName = uploadInfo.file.name
          const title = fileName.substring(0, fileName.lastIndexOf('.'))

          // 将相关的视频id和视频名存入数据库
          self.video.videoOriginalName = title
          self.video.videoSourceId = uploadInfo.videoId
        },
        // 文件上传失败
        'onUploadFailed': function(uploadInfo, code, message) {
          console.log('onUploadFailed...')
          console.log('onUploadFailed: file:' + uploadInfo.file.name + ',code:' + code + ', message:' + message)
          self.statusText = '文件上传失败!'
        },

        // 文件上传进度，单位：字节
        'onUploadProgress': function(uploadInfo, totalSize, progress) {
          console.log('onUploadProgress...')
          console.log('onUploadProgress:file:' + uploadInfo.file.name + ', fileSize:' + totalSize + ', percent:' + Math.ceil(progress * 100) + '%')
          const progressPercent = Math.ceil(progress * 100)
          self.authProgress = progressPercent + '%'
          self.statusText = '文件上传中...'
        },
        // 上传凭证超时
        'onUploadTokenExpired': function(uploadInfo) {
          console.log('onUploadTokenExpired...')
          // 上传大文件超时, 如果是上传方式一即根据 UploadAuth 上传时
          // 需要根据 uploadInfo.videoId 调用刷新视频上传凭证接口(https://help.aliyun.com/document_detail/55408.html)重新获取 UploadAuth
          // 然后调用 resumeUploadWithAuth 方法
          vod.refreshUploadAuthAndAddress(uploadInfo.videoId).then(response => {
            console.log(response)
            const uploadAuth = response.data.response.uploadAuth
            // 上传凭证失效后恢复上传
            uploader.resumeUploadWithAuth(uploadAuth)
            console.log('upload expired and resume upload with uploadauth ' + uploadAuth)
          })
          self.statusText = '文件超时重新获取上传凭证...'
        },
        // 全部文件上传结束
        'onUploadEnd': function(uploadInfo) {
          console.log('onUploadEnd...')
          self.statusText = '文件上传完毕'
        }
      })

      return uploader
    }
  }
}
</script>
