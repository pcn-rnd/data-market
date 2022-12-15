<script>
    import Layout from '../../layouts/main'
    import PageHeader from '@/components/page-header'
    import appConfig from "@/app.config";
    import Swal from "sweetalert2";
    import Multiselect from "vue-multiselect";
    import axios from "axios";

    /**
     * Indices component
     */
    export default {
        page: {
            title: "Indices",
            meta: [{name: "description", content: appConfig.description}]
        },
        components: {Layout, PageHeader, Multiselect},
        data() {
            return {
                title: 'Indices',
                items: [
                    {
                        text: 'Home',
                        href: '/',
                    },
                    {
                        text: 'Indices',
                        active: true,
                    },
                ],
                indices: [],
                configs: [],
                type_options: [
                    "text",
                    "keyword",
                    "long",
                    "integer",
                    "short",
                    "double",
                    "float",
                    "date",
                    "boolean",
                    "object"
                ],
                maps: {},
                indexName: ""
            }
        },
        created() {
            this.resetStats();
        },
        methods: {
            resetStats() {
                this.$http({
                    method: 'get',
                    url: '/indices?withStats=true&withMappings=true'
                })
                .then(response => {
                    this.indices = response.data.body;
                })
                .catch(error => {
                    return error;
                })
            },
            createIndex(index) {
                this.$http({
                    method: 'post',
                    url: `/indices/${index}/:create?overwrite=true`
                })
                // eslint-disable-next-line no-unused-vars
                .then(response => {
                    Swal.mixin({}).fire(
                        "재생성 완료!",
                        `${index} 인덱스가 재생성 되었습니다`,
                        "success"
                    );
                    this.initForm();
                    this.resetStats();
                })
                .catch(error => {
                    return error;
                })
            },
            deleteIndex(index) {
                this.$http({
                    method: 'delete',
                    url: `/indices/${index}`
                })
                // eslint-disable-next-line no-unused-vars
                .then(response => {
                    Swal.mixin({}).fire(
                        "삭제 완료!",
                        `${index} 인덱스가 삭제 되었습니다`,
                        "success"
                    );
                    this.resetStats();
                })
                .catch(error => {
                    return error;
                })
            },
            deleteIndexConfirm(index) {
                const swalWithBootstrapButtons = Swal.mixin({
                    customClass: {
                        confirmButton: "btn btn-success",
                        cancelButton: "btn btn-danger ml-2"
                    },
                    buttonsStyling: false
                });

                swalWithBootstrapButtons
                .fire({
                    title: `${index} 인덱스를 삭제 하시겠습니까?`,
                    text: "삭제한 인덱스는 복구 할 수 없습니다.",
                    icon: "warning",
                    confirmButtonText: "삭제 합니다!",
                    cancelButtonText: "취소!",
                    showCancelButton: true
                })
                .then(result => {
                    if (result.value) {
                        this.deleteIndex(index)
                    }
                });
            },
            createIndexConfirm(index) {
                const swalWithBootstrapButtons = Swal.mixin({
                    customClass: {
                        confirmButton: "btn btn-success",
                        cancelButton: "btn btn-danger ml-2"
                    },
                    buttonsStyling: false
                });

                swalWithBootstrapButtons
                .fire({
                    title: `${index} 인덱스를 재생성 하시겠습니까?`,
                    text: "데이터는 삭제되어 복구 할 수 없습니다.",
                    icon: "warning",
                    confirmButtonText: "재생성 합니다!",
                    cancelButtonText: "취소!",
                    showCancelButton: true
                })
                .then(result => {
                    if (result.value) {
                        this.createIndex(index)
                    }
                });
            },
            initForm() {
                this.configs = [];
                this.indexName = "";
            },
            addField() {
                this.configs.push({fieldName: "", type: "", korean: true, autoCompletable: false, indexable: true});
            },
            deleteRow(index) {
                if (confirm("Are you sure you want to delete this element?")) {
                    this.configs.splice(index, 1);
                }
            },
            /* eslint-disable no-console */
            submitConfig() {
                axios.put(`/indices/${this.indexName}`, {
                    configs: this.configs
                }).then(response => {
                    console.log(response);
                    this.resetStats()
                })
            },
            /* eslint-disable no-console */
            getMapping(indexName) {
                axios.get(`/indices/${indexName}/mappings`, {}).then(response => {
                    this.indexName = indexName;
                    this.configs = response.data.body;
                })
            }
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
                <div class="search-box mr-2 mb-2 d-inline-block">
                  <!--                  <div class="position-relative">-->
                  <!--                    <input type="text" class="form-control" placeholder="Search..."/>-->
                  <!--                    <i class="bx bx-search-alt search-icon"></i>-->
                  <!--                  </div>-->
                </div>
              </div>
              <div class="col-sm-8">
                <div class="text-sm-right">
                  <button type="button" class="btn btn-success btn-rounded mb-2 mr-2" v-b-modal.mappings-form variant="primary" @click="initForm">
                    <i class="mdi mdi-plus mr-1"></i> 인덱스 생성
                  </button>

                  <b-modal
                      id="mappings-form"
                      scrollable
                      size="xl"
                      title="Index Schema Settings"
                      title-class="font-18"
                      @ok="submitConfig"
                  >
                    <h4 class="card-title mb-4">Index Name</h4>
                    <div class="form-group col-lg-auto">
                      <b-form-input for="text" v-model="indexName"></b-form-input>
                    </div>

                    <hr>
                    <h4 class="card-title mb-4">Fields</h4>
                    <div class="table-responsive">
                      <table class="table table-centered table-nowrap table-hover">
                        <thead class="thead-light">
                        <tr>
                          <th scope="col" style="width: 70px;"></th>
                          <th scope="col" style="width: 200px;">Name</th>
                          <th scope="col" style="width: 200px;">Type</th>
                          <th scope="col">Korean</th>
                          <th scope="col">Auto complete</th>
                          <th scope="col">Indexable</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(config, index) in configs" :key="config.id">
                          <td>
                            <button type="button" class="btn btn-danger btn-rounded btn-sm rounded-pill" @click.prevent="deleteRow(index)">
                              <i class="bx bx-minus align-middle mr-0"></i>삭제
                            </button>
                          </td>
                          <td>
                            <b-form-input class="col-lg-auto" for="text" v-model="config.fieldName"></b-form-input>
                          </td>
                          <td>
                            <multiselect class="col-lg-auto" :options="type_options" v-bind:id="'type_'+index" v-model="config.type"></multiselect>
                          </td>
                          <td>
                            <div class="custom-control custom-checkbox mb-0">
                              <input type="checkbox" class="custom-control-input" v-model="config.korean" v-bind:id="'korean_'+index"/>
                              <label class="custom-control-label" v-bind:for="'korean_'+index">한글 색인</label>
                            </div>
                          </td>
                          <td>
                            <div class="custom-control custom-checkbox mb-0">
                              <input type="checkbox" class="custom-control-input" v-model="config.autoCompletable"
                                     v-bind:id="'auto_completable_'+index"/>
                              <label class="custom-control-label" v-bind:for="'auto_completable_'+index">자동완성 포함</label>
                            </div>
                          </td>
                          <td>
                            <div class="custom-control custom-checkbox mb-0">
                              <input type="checkbox" class="custom-control-input" v-model="config.indexable" v-bind:id="'indexable_'+index"/>
                              <label class="custom-control-label" v-bind:for="'indexable_'+index">색인 대상</label>
                            </div>
                          </td>
                        </tr>
                        </tbody>
                      </table>
                    </div>

                    <input
                        type="button"
                        class="btn btn-success mt-3 mt-lg-0"
                        value="Add"
                        @click="addField"
                    />

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
                  <th>인덱스</th>
                  <th>문서 수</th>
                  <th>데이터 용량</th>
                  <th>스키마 수정일</th>
                  <th>관리</th>
                </tr>
                </thead>
                <tbody class="text-center">
                <tr v-for="index in indices" :key="index.index">
                  <td>
                    <strong class="pr-1">{{index.name}}</strong>
                    <span class="badge badge-danger font-size-11 mr-1" v-if="index.mappings == null">no schema</span>
                  </td>
                  <td>{{index.stats.primaries.docs.count}}</td>
                  <td>{{index.stats.primaries.store.size_in_bytes}}</td>
                  <td>
                    <template v-if="index.mappings != null">{{index.mappings.modified}}</template>
                    <span class="badge badge-warning font-size-11 ml-1" v-if="index.mappings != null && !index.mappings.applied">Not applied</span>
                  </td>
                  <td>
                    <div class="button-items">
                      <button type="button" class="btn btn-success btn-sm" v-b-modal.mappings-form @click.prevent="getMapping(index.name)">
                        <i class="bx bx-edit font-size-16 align-middle mr-0"></i> 변경
                      </button>
                      <button type="button" class="btn btn-warning btn-sm" @click.prevent="createIndexConfirm(index.name)"
                              v-if="index.mappings != null && !index.mappings.applied">
                        <i class="bx bx-rotate-right font-size-16 align-middle mr-0"></i> 재생성
                      </button>
                      <button type="button" class="btn btn-danger btn-sm" @click.prevent="deleteIndexConfirm(index.name)">
                        <i class="bx bx-trash font-size-16 align-middle mr-0"></i> 삭제
                      </button>
                    </div>
                  </td>
                </tr>

                </tbody>
              </table>

            </div>
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
