<script>
  import Layout from "../../layouts/main";
  import PageHeader from "@/components/page-header";
  import appConfig from "@/app.config";
  import axios from "axios"
  //import VueJsonPretty from 'vue-json-pretty'

  /**
   * Workspace component
   */
  export default {

    page: {
      title: "Create New Dataset",
      meta: [{name: "description", content: appConfig.description}]
    },
    components: {Layout, PageHeader},
    data() {
      return {
        title: "Create New Dataset",
        items: [
          {
            text: "Dataset",
            href: "/"
          },
          {
            text: "Create New Dataset",
            active: true
          }
        ],
        dataset : {
            setId : null,
            catId : {},
            wsId : 0,
            title : "",
            content : "",
            datatype : "",
            path : "",
            crtDt : null,
            urlsInfo : null,
            publicYn : "N",
            views : 0,
            seller : {
                userId : "pcn"
            } /// 현재 사용자 수정 必
        },
        workspace : {
            wsId : 0,
            wsName : "",
            wsDs : "",
            lastWork : null,
            lastWorkComt : null,
            wsCrtDt : null,
            wsModDt : null,
            percent : null,
            userLockYn : "\u0000",
            logFilePath : null,
            user : null,
            filePath : null,
            datatype : null
        },
        categories : {},
        wss : {},
        urlsInfo :[],
        types : ['PDF', 'CSV', 'TEXT'],
        bcApiHost : "",
        fileList : [],
        uldStatus : 'STANDBY',
        wsCurSqe : null
      };
    },
    created() {
        //변수 명 앞에 항상 VUE_APP_ 를 붙여주면 Vue CLI에서 내부적으로 웹팩 DefinePlugin을 활용하여 클라이언트 웹 자원에서 접근할 수 있게 설정
        this.bcApiHost =  process.env.VUE_APP_BC_API_HOST;
        console.log(this.bcApiHost);
        this.getCats();
        //this.getWss();
    },
    methods: {
        getCats() {
            axios.get('/cat', {
            }).then(response => {
                this.categories = response.data.body;
            })
        },
        async create() {
            this.pre();
            console.log(this.dataset);

            if(this.uldStatus === 'COMPLETED') {
                try{
                    this.workspace.wsName = this.dataset.title;
                    await axios.post (this.bcApiHost + '/api/wss', this.workspace)
                        .then(response => {
                            if(response.status === 200)
                                this.dataset.wsId = response.data.body;
                        });

                    await axios.post('/ds', this.dataset)
                        .then(response => {
                            if(response.status === 200) {
                                this.$router.push({path : '/'})
                            }
                        });
                }catch (e) {
                    alert(e);
                }
            }else {
                alert("파일 업로드를 완료하십시오.")
            }
        },
        addUrlInfo() {
            this.urlsInfo.push({datatype : "", description : ""})
        },
        pre() {
            if(this.dataset.publicYn === false) this.dataset.publicYn = 'N';
            else this.dataset.publicYn = 'Y';
            console.log(this.urlsInfo);
            this.dataset.urlsInfo = JSON.stringify(this.urlsInfo);
            this.dataset.crtDt = new Date();
        },
        deleteInfo() {
            this.urlsInfo.splice(0, 1);
        },
        uploadFile() {
            this.uldStatus = 'UPLOADING';
            let formData = new FormData();

            console.log(this.$refs.picked_files.files);
            for(let i = 0; i < this.$refs.picked_files.files.length; i++) {
                formData.append('files', this.fileList[i]);
            }
            axios.post('/file', formData
            ).then(response => {
                if(response.status === 200){
                    this.uldStatus = 'COMPLETED';
                    this.workspace.filePath = response.data.body.savePath;
                    this.dataset.path = response.data.body.savePath;
                }else{
                    alert(response.statusText)
                }
            });

        },
        updateFList() {
            this.fileList = Array.from(this.$refs.picked_files.files);
            this.uldStatus = 'STANDBY';
        },
        deleteFList(idx) {
            this.fileList.splice(idx, 1);
        },
        selectType(index, event) {
            console.log(index);
            this.dataset.datatype[index] = event.target.value;
            console.log(this.dataset.datatype);
        }
    }
  };
</script>

<template>
    <Layout>
        <PageHeader :title="title" :items="items"/>
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title mb-4">Create New Dataset</h4>
                        <form>
                            <div class="form-group row mb-4">
                                <label for="projectCat" class="col-form-label col-lg-2">Category</label>
                                <select id="projectCat" v-model="dataset.catId.catId" class="form-control col-md-3 ml-2 mt-1">
                                    <option v-for="(item, idx) in categories"
                                            :key="idx"
                                            :value="item.catId"
                                    >{{item.catName}}</option>
                                </select>
                            </div>
                            <div class="form-group row mb-4">
                                <label for="projectName" class="col-form-label col-lg-2">Dataset Name</label>
                                <div class="col-lg-10">
                                  <input
                                      id="projectName"
                                      name="projectName"
                                      type="text"
                                      class="form-control"
                                      v-model="dataset.title"
                                      placeholder="Enter Dataset Name..."
                                  />
                                </div>
                            </div>
                            <div class="form-group row mb-4">
                                <label for="projectDesc" class="col-form-label col-lg-2">Dataset Description</label>
                                <div class="col-lg-10" >
                                  <textarea
                                      id="projectDesc"
                                      class="form-control"
                                      rows="10"
                                      v-model="dataset.content"
                                      placeholder="Enter Dataset Description..."
                                  ></textarea>
                                </div>
                            </div>
                            <div class="form-group row mb-4 ">
                                <label  class="col-form-label col-lg-2 font-weight-bold">데이터셋 공개여부</label>
                                <div class="custom-control custom-checkbox ml-3">
                                    <input type="checkbox" class="custom-control-input" id="publicYn" v-model="dataset.publicYn"/>
                                    <label class="custom-control-label" for="publicYn">(체크 시 공개)</label>
                                </div>
                            </div>



                            <div class="form-group row mb-4">
                                <label for="projectdesc" class="col-form-label col-lg-2 font-weight-bold">요청 URL 정보</label>

                                <table class="table table-bordered ml-3" style="width: 70%;" id="infos">
                                    <thead>
                                    <tr>
                                        <th>데이터 형식</th>
                                        <th>설명</th>
                                        <th>
                                            <input
                                                type="button"
                                                class="btn btn-success"
                                                style="padding : 0px 5px;"
                                                value="+"
                                                @click="addUrlInfo" />
                                        </th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                        <tr v-for="(info, index) in urlsInfo" :key="info.id" >
                                            <td>
                                                <select :id="`types_${index}`" v-model="info.datatype" class="form-control col-md-12" @change="selectType(index, $event)">
                                                    <option v-for="(item, idx) in types" :key="idx" :id="`typ_opt_${index}_${idx}`"

                                                    >{{item}}</option>
                                                </select>

                                            </td>
                                            <td><b-form-input for="text" value="Artisanal kale" v-model="info.description"></b-form-input></td>
                                            <td>
                                                <input
                                                        type="button"
                                                        class="btn btn-primary"
                                                        style="padding : 0px 6px;"
                                                        value="-"
                                                        @click="deleteInfo(index)"
                                                />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>




                            <form class="form-group row mb-4" enctype="multipart/form-data">
                                <label class="col-form-label col-lg-2">데이터 업로드</label>
                                <div class="custom-file col-4 ml-3">
                                    <input multiple="multiple" id="customFile" type="file" class="custom-file-input" ref="picked_files" @change="updateFList"/>
                                    <label class="custom-file-label" for="customFile">Choose file</label>
                                </div>
                                <div class="m-2" v-if="uldStatus === 'UPLOADING'">
                                    <span>Uploading. . .</span>
                                    <b-spinner class="ml-2 spinner-border-sm" variant="danger" role="status"></b-spinner>
                                </div>
                                <div class="m-2" v-if="uldStatus === 'COMPLETED'">
                                    <span>Upload Completed</span>
                                    <i class="bx bx-check-circle font-size-22 align-middle text-primary ml-2"></i>
                                </div>
                                    <!--<select id="projectWs" v-model="dataset.wsId" class="form-control col-md-3 ml-3 mt-1">
                                    <option v-for="(item, idx) in wss"
                                            :value="item.wsId"
                                            :key="idx"
                                    >{{item.wsName}}</option>
                                </select>-->
                               <!-- <div class="mt-sm-3 ml-2">
                                    <a :href='bcApiHost' ><i class="bx bx-link-external"></i> 워크스페이스 생성</a>
                                </div>-->

                                <b-button class="btn btn-success ml-5" @click="uploadFile()">UPLOAD</b-button>
                            </form>


                            <div class="form-group row mb-4" v-if="fileList.length != 0">
                                <label  class="col-form-label col-lg-2"></label>
                                <div class="col-lg-10 pl-4">
                                    <p class="text-muted" v-for="(file, idx) in fileList" :key="idx">
                                        <i class="bx bx-paperclip font-size-16 align-middle text-primary mr-1"></i> {{file.name}}
                                        <i class="bx bx-x font-size-16 align-middle mr-1" @click="deleteFList(idx)"></i>
                                    </p>
                                </div>
                            </div>

                            <div class="row justify-content-end">
                                <div class="col-lg-10 text-sm-right button-items">
                                    <b-button class="btn btn-success" @click="create()">Create</b-button>
                                    <router-link tag="a" to="/">
                                        <button class="btn btn-warning">Cancel</button>
                                    </router-link>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
  </Layout>
</template>
