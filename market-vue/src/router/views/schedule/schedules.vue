<script>
    import Layout from '../../layouts/main'
    import PageHeader from '@/components/page-header'
    import appConfig from "@/app.config";
    import Multiselect from "vue-multiselect";
    import axios from 'axios'

    /**
     * Schedules component
     */
    export default {
        page: {
            title: "Schedules",
            meta: [{name: "description", content: appConfig.description}]
        },
        components: {Layout, PageHeader, Multiselect},
        data() {
            return {
                title: 'Schedules',
                items: [
                    {
                        text: 'Home',
                        href: '/',
                    },
                    {
                        text: 'Schedules',
                        active: true,
                    },
                ],
                schedules: [],
                schedule: {
                    id: "",
                    name: "",
                    type: "",
                    pipeline: "",
                    value: ""
                },
                type_options: [
                    "CRON",
                    "PERIODIC"
                ],
                pipeline: {
                    value: "",
                    options: []
                }
            }
        },
        created() {
            /* eslint-disable no-console */
            this.getPipelines().then(() => this.resetSchedules());
        },
        methods: {
            resetSchedules() {
                axios.get('/collector/schedules', {}).then(response => {
                    this.schedules = response.data.body;
                })
            },
            deleteSchedule(scheduleId) {
                axios.delete(`/collector/schedules/${scheduleId}`, {}).then(response => {
                    console.log(response);
                    this.schedules = response.data.body;
                })
            },
            createSchedule() {
                this.setRequest()
                axios.put('/collector/schedules', this.schedule)
                .then(response => {
                    console.log(response)
                    this.schedules = response.data.body;
                })
            },
            modifySchedule() {
                this.setRequest()
                axios.put(`/collector/schedules/${this.schedule.id}`, this.schedule).then(response => {
                    console.log(response);
                    this.schedules = response.data.body;
                })
            },
            getSchedule(scheduleId) {
                axios.get(`/collector/schedules/${scheduleId}`).then(response => {
                    this.schedule = response.data.body
                    this.pipeline.value = this.pipeline.options.find(pipeline => pipeline.id === this.schedule.pipeline)
                })
            },
            clearInputs() {
                this.schedule = {}
                this.pipeline.value = ''
            },
            getPipelines() {
                return axios.get('/collector/pipelines')
                .then(response => {
                    this.pipeline.options = response.data.body;
                })
                .catch(error => {
                    console.log(error);
                })
            },
            setRequest() {
                this.schedule.pipeline = this.pipeline.value.id;
            },
            pipelineName(id) {
                if (this.pipeline.options.length > 0) {
                    return this.pipeline.options.find(pipeline => pipeline.id === id).name
                }
            }
            /* eslint-disable no-console */
        }
    }
</script>

<template>
  <Layout>
    <PageHeader :title="title" :items="items"/>
    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <div class="row mb-2">
              <div class="col-sm-4">

              </div>
              <div class="col-sm-8">
                <div class="text-sm-right">
                  <button type="button" class="btn btn-success btn-rounded mb-2 mr-2" v-b-modal.schedule-form variant="primary"
                          @click.prevent="clearInputs">
                    <i class="mdi mdi-plus mr-1"></i> 스케줄러 생성
                  </button>

                  <b-modal
                      id="schedule-form"
                      size="xl"
                      title="Create Schedule"
                      title-class="font-18"
                      @ok="createSchedule"
                  >
                    <div class="table-responsive">
                      <table class="table table-centered table-nowrap table-hover" style="margin-bottom: 100px">
                        <thead class="thead-light">
                        <tr>

                          <th scope="col" style="text-align: center;">Name</th>
                          <th scope="col" style="text-align: center;">Description</th>
                          <th scope="col" style="text-align: center;">Pipeline</th>
                          <th scope="col" style="text-align: center;">Type</th>
                          <th scope="col" style="text-align: center;">Value</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>

                          <td>
                            <b-form-input class="col-lg-auto" for="text" v-model="schedule.name"></b-form-input>
                          </td>
                          <td>
                            <b-form-input class="col-lg-auto" for="text" v-model="schedule.description"></b-form-input>
                          </td>
                          <td>
                            <multiselect class="col-lg-auto" :options="pipeline.options" v-model="pipeline.value" label="name"></multiselect>
                          </td>
                          <td>
                            <multiselect class="col-lg-auto" :options="type_options" v-model="schedule.type"></multiselect>
                          </td>
                          <td>
                            <b-form-input class="col-lg-auto" for="text" v-model="schedule.value"></b-form-input>
                          </td>
                        </tr>
                        </tbody>
                      </table>
                    </div>

                  </b-modal>

                </div>
              </div>
              <!-- end col-->
            </div>
            <!-- Table data -->
            <div class="table-responsive mb-0">
              <table class="table table-centered table-nowrap">
                <thead class="thead-light font-size-14 text-center">
                <tr>
                  <th>스케줄러 이름</th>
                  <th>설명</th>
                  <th>파이프라인</th>
                  <th>타입</th>
                  <th>시간 값</th>
                  <th>관리</th>
                </tr>
                </thead>
                <tbody class="text-center">
                <tr v-for="(schedule) in schedules" :key="schedule.id">
                  <td>
                    <strong class="pr-1">{{schedule.name}}</strong>
                  </td>
                  <td>{{schedule.description}}</td>
                  <td>{{pipelineName(schedule.pipeline)}}</td>
                  <td>{{schedule.type}}</td>
                  <td>{{schedule.value}}</td>
                  <td>
                    <div class="button-items">
                      <button type="button" class="btn btn-info btn-sm" v-b-modal.schedule-modify-form @click.prevent="getSchedule(schedule.id)">
                        <i class="bx bx-edit font-size-16 align-middle mr-0"></i> 변경
                      </button>
                      <button type="button" class="btn btn-danger btn-sm" @click.prevent="deleteSchedule(schedule.id)">
                        <i class="bx bx-trash font-size-16 align-middle mr-0"></i> 삭제
                      </button>
                    </div>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
            <b-modal
                id="schedule-modify-form"
                size="xl"
                title="Modify Schedule"
                title-class="font-18"
                @ok="modifySchedule"
            >
              <div class="table-responsive">
                <table class="table table-centered table-nowrap table-hover" style="margin-bottom: 100px">
                  <thead class="thead-light">
                  <tr>

                    <th scope="col" style="text-align: center;">Name</th>
                    <th scope="col">Desc</th>
                    <th scope="col" style="text-align: center;">Pipeline</th>
                    <th scope="col" style="text-align: center;">Type</th>
                    <th scope="col" style="text-align: center;">Value</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td>
                      <b-form-input class="col-lg-auto" for="text" v-model="schedule.name"></b-form-input>
                    </td>
                    <td>
                      <b-form-input class="col-lg-auto" for="text" v-model="schedule.description"></b-form-input>
                    </td>
                    <td>
                      <multiselect class="col-lg-auto" :options="pipeline.options" v-model="pipeline.value" label="name"></multiselect>
                    </td>
                    <td>
                      <multiselect class="col-lg-auto" :options="type_options" v-model="schedule.type"></multiselect>
                    </td>
                    <td>
                      <b-form-input class="col-lg-auto" for="text" v-model="schedule.value"></b-form-input>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>

            </b-modal>

          </div>
          <!-- end card-body -->
        </div>
        <!-- end card -->
      </div>
      <!-- end col -->
    </div>
    <!-- end row -->
  </Layout>
</template>
