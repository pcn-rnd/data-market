<script>
  import Layout from "../../layouts/main";
  import PageHeader from "@/components/page-header";
  import appConfig from "@/app.config";
  import axios from 'axios';

  /**
   * Cooperation List Component
   */
  export default {
    page: {
      title: "Cooperation List",
      meta: [{name: "description", content: appConfig.description}]
    },
    components: {Layout, PageHeader},
    data() {
      return {
        title: "Cooperation List",
        items: [
          {
            text: "Cooperation",
            active: true
          }
        ],
        workspace: {},
        paging : {},
        defaultPgSize : 10,
        works : {}
      };
    },
    created() {
      this.getWsList()
    },
    methods : {
      getWsList() {
        axios.get(`/wss?size=${this.defaultPgSize}&sort=wsId&page=${this.workspace.number-1}`, {
        }).then(response =>{
          this.workspace = response.data.body
          for(let i = 0; i < this.workspace.content.length; i++) {
            this.getWorks(this.workspace.content[i].wsId)
          }
          this.workspace.number += 1
        })
      },
      getWorks(id){
        axios.get(`wss/${id}/works`)
        .then(response=>{
          this.$set(this.works, `${id}` ,response.data.body);
        })
      },
      pageChange(){
        this.getWsList()
      }
    }
  }

</script>
<template>
  <Layout>
  <PageHeader :title="title" :items="items"/>
  <div class="card">
    <div class="card-body" >
      <h3 class="font-weight-bold">협업 이력 <span class="font-size-15">({{workspace.totalElements}})</span></h3>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <div class="table-responsive">
              <table class="table mb-0" >
                <thead class="thead-light">
                  <tr>
                    <th>Workspace 이름</th>
                    <th>수집</th>
                    <th>전처리</th>
                    <th>META 프로파일링</th>
                    <th>시각화 프로파일링</th>
                    <th>진척률</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(v, idx) in workspace.content" v-bind:key="idx">
                    <th scope="row" style="vertical-align: middle;">{{v.wsName}}</th>
                    <td v-for="(work, idx) in works[v.wsId]" v-bind:Key="idx" >
                      <div class="card" style="float:left; width:92%;">
                        <div class="card-body p-2">
                          <div class="media" style="overflow: hidden">
                            <div class="media-body">
                            </div>
                          </div>
                          <div class="plan-features">
                            <p class="p-list">
                              <i class="bx bx-checkbox-square text-primary mr-2"/>
                              <span class="font-weight-bold">작성자 : </span>
                              <span>{{work.worker}}</span>
                            </p>
                            <p class="p-list">
                              <i class="bx bx-checkbox-square text-primary mr-2"/>
                              <span class="font-weight-bold">코멘트 : </span>
                              <span>{{work.workerCmt}}</span>
                            </p>
                            <p class="p-list" v-if="work.worksMultiId.workId === 0">
                              <i class="bx bx-checkbox-square text-primary mr-2"/>
                              <span class="font-weight-bold">데이터 처리량 : </span>
                              <span>{{work.thput}}</span>
                            </p>
                            <p class="p-list" v-if="work.worksMultiId.workId != 0">

                            </p>
                            <p class="p-list font-size-14 font-weight-bold" v-if="work.finishYn === 'Y'">
                              작업 완료
                            </p>
                            <p class="p-list font-size-14 font-weight-bold" v-else>
                              작업 미완료
                            </p>
                          </div>
                        </div>
                      </div>
                      <div class= "vertical-center" style="float:right;" v-if="work.worksMultiId.workId != 3">
                        <i class="mdi mdi-arrow-right ml-1"/>
                      </div>
                    </td>
                    <td style="padding : 20px;" class= "vertical-center">
                      <span class="font-weight-bold font-size-22">{{v.percent}}%</span>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
    <b-pagination
        prev-text="이전"
        next-text="다음"
        align="center"
        v-model="workspace.number"
        :total-rows="workspace.totalElements"
        :per-page="10"
        @input="pageChange"></b-pagination>
  </div>
  </Layout>
</template>
