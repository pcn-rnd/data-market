<script>
  import Layout from "../../layouts/main";
  import PageHeader from "@/components/page-header";
  import appConfig from "@/app.config";
  import axios from "axios"


  /**
   * Dashboard Component
   */
  export default {
    page: {
      title: "Dashboard",
      meta: [{name: "description", content: appConfig.description}]
    },
    components: {Layout, PageHeader},
    data() {
      return {
        title: "Dashboard",
        items: [
          {
            text: "Workspace",
            href: "/"
          },
          {
            text: "Dashboard",
            active: true
          }
        ],
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
          user : null
        },
        curId : 0,
        works : [],
        shows : [true, true, true, true],
        wsId : 0,
        curUser : "",
        textarea : "",
        token : ""
      };
    },
    created() {
      this.token = this.$store.state.token
      this.getCurrentUser()
      this.wsId = this.$route.query.id
      this.getWsInfo(this.wsId)
      this.getWorks(this.wsId)
    },
    methods: {
      getWsInfo(id){
        axios.get(`wss/${id}`)
        .then(response=>{
          this.workspace = response.data.body;
          if(response.status == 200) {
            this.getLog()

          }
        })
      },
      getWorks(id){
        axios.get(`wss/${id}/works`)
        .then(response=>{
          this.works = response.data.body
          if(response.status == 200) {
            this.getLog()
          }
        })
      },
      editWorks(idx){
        this.$set(this.shows, idx, !this.shows[idx])
        this.getWorks(this.wsId)
      },
      changeStat(idx){
        this.works[idx].worker = this.curUser;
        axios.put('wss/works', this.works[idx])
        .then(response => {
          this.$set(this.shows, idx, !this.shows[idx])
          if(response.status == 200) {
            this.getLog()
          }
        })
      },
      getCurrentUser(){
        axios.get('auth/current').then(response => {
          this.curUser = response.data.body
        })
      },
      toGo(){
        location.href= 'http://localhost:8082?Authorization=' + this.token
      },
      getLog() {
        axios.get('wss/log').then(response => {
          let log = response.data.body
          this.textarea += log.logLevel + " " + log.time + " " + log.className + " " + log.logMessage + "\n"
        })
      },
      getToken() {

      }

    }
  };
</script>

<template>
  <Layout>
    <PageHeader :title="title" :items="items"/>
    <div class="row">
      <div class="col-lg-12">
        <div class="button-items" style="margin-bottom: 5px;">
          <!--<a v-bind:href="'http://localhost:8082?Authorization=' + token">수집기 모듈</a>-->
          <b-button variant="info" class="left-button" @click="toGo">수집기 모듈</b-button>
          <b-button variant="info" class="left-button">전처리 모듈</b-button>
          <b-button variant="info" class="left-button">META 프로파일링 모듈</b-button>
          <b-button variant="info" class="left-button">시각화 프로파일링 모듈</b-button>
          <b-button variant="danger" class="float-right">리포트 생성</b-button>
        </div>
        <div class="card">
          <div class="card-body">
            <h4 class="card-title mb-4">{{workspace.wsName}}</h4>
            <h6>{{workspace.wsDs}}</h6>
          </div>
        </div>
        <div class="card">
          <div class="card-body">
            <h4 class="card-title mb-4">Horizontal Timeline</h4>
            <div class="hori-timeline">
              <ul class="list-inline events">
                <li class="list-inline-item event-list" v-for="(v, idx) in works" v-bind:Key="idx">
                  <div class="mt-4">
                    <div class="event-up-icon">
                      <i v-if="v.finishYn === 'Y'"
                          class="bx bx-up-arrow-circle h1 text-primary up-arrow-icon"
                      ></i>
                      <i v-else
                         class="bx bx-up-arrow-circle h1 text-primary bx-fade-up-hover up-arrow-icon bx-fade-up"
                      ></i>
                    </div>
                    <div class="event-date mt-3">
                      <div class="text-primary" v-if="v.worksMultiId.workId === 0">수집</div>
                      <div class="text-primary"  v-if="v.worksMultiId.workId === 1">전처리</div>
                      <div class="text-primary" v-if="v.worksMultiId.workId === 2">META 프로파일링</div>
                      <div class="text-primary" v-if="v.worksMultiId.workId === 3">시각화 프로파일링</div>
                      <h5 class="mt-2" v-if="v.finishYn === 'Y'">완료</h5>
                      <h5 class="mt-2" v-else>미완료</h5>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div v-for="(v, idx) in works" v-bind:Key="idx">
          <div class="card">
            <div class="card-body">
              <h4 class="card-title font-size-18" v-if="v.worksMultiId.workId === 0">수집</h4>
              <h4 class="card-title font-size-18" v-if="v.worksMultiId.workId === 1">전처리</h4>
              <h4 class="card-title font-size-18" v-if="v.worksMultiId.workId === 2">META 프로파일링</h4>
              <h4 class="card-title font-size-18" v-if="v.worksMultiId.workId === 3">시각화 프로파일링</h4>
              <hr/>
              <h6><span class="font-weight-bold">파일 저장 위치 : </span><span>{{v.filePath}}</span></h6>
              <h6 v-if="v.worksMultiId.workId === 0"><span class="font-weight-bold">데이터 처리량 : </span><span>{{v.thput}}</span></h6>
              <b-card header-class="bg-transparent border-success" class="border border-dark" :id='`edit-comts-${v.worksMultiId.workId}`' v-if="shows[idx]">
                <a class="float-right" href="javascript:void(0)" @click="editWorks(idx)">편집</a>
                <h6 class="font-weight-bold">작성자 : <span>{{v.worker}}</span></h6>
                <h6 class="font-weight-bold">Comment : {{v.workerCmt}}</h6>
              </b-card>
              <b-card header-class="bg-transparent border-success" class="border border-dark" :id='`edit-comts-${v.worksMultiId.workId}`' v-if="!shows[idx]">
                <a class="float-right" href="javascript:void(0)" @click="editWorks(idx)">취소</a>
                <a class="float-right" href="javascript:void(0)" @click="changeStat(idx)" style="margin-right: 5px;">확인</a>
                <div  class="mb-1">
                  <b-form-group
                      id="example-search"
                      label="Comment"
                      label-for="search"
                  >
                    <b-form-input
                        id="search"
                        v-model="v.workerCmt"
                        type="search"
                        name="search"
                    ></b-form-input>
                  </b-form-group>
                </div>
              </b-card>
              <hr/>
              <h3 class="font-weight-bold" v-if="v.finishYn === 'Y'">완료</h3>
              <h3 class="font-weight-bold" v-else>미완료</h3>
            </div>
          </div>
        </div>
        <div class="card">
          <div class="card-body">
            <h4 class="font-weight-bold">협업 로그</h4>
            <hr/>
            <div>

              <textarea
                  v-model="textarea"
                  class="form-control"
                  :maxlength="225"
                  rows="13"
                  placeholder="This textarea has a limit of 225 chars."
                  readonly
              >


              </textarea>
              <!--<div class="text-center">
                <p
                    v-if="textarea"
                    class="badge position-absolute"
                    :class="{ 'badge-success': textarea.length !== 225,
                            'badge-danger': textarea.length === 225 }"
                >
                  {{ textarea.length }} /
                  225
                </p>
              </div>-->
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- end row -->
  </Layout>
</template>
