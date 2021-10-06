<script>
    import Layout from "../../layouts/main";
    import PageHeader from "@/components/page-header";
    import appConfig from "@/app.config";
    import Multiselect from "vue-multiselect";
    import VueJsonPretty from 'vue-json-pretty'

    /**
     * Documents component
     */
    export default {
        page: {
            title: "Documents",
            meta: [{name: "description", content: appConfig.description}]
        },
        components: {Layout, PageHeader, Multiselect, VueJsonPretty},
        data() {
            return {
                title: "Documents",
                items: [
                    {
                        text: "Home",
                        href: "/"
                    },
                    {
                        text: "Documents",
                        active: true
                    }
                ],
                form: {
                    keyword: "",
                    exceptedKeyword: "",
                    index: "",
                    indices: [],
                    exactly: false,
                    page: 1
                },
                document: {
                    total: 0,
                    documents: [],
                    origin: {},
                    showOrigin: false
                }
            };
        },
        computed: {
            searchIndex: function () {
                let index = []

                for (let i = 0; i < this.form.index.length; i++) {
                    index.push(this.form.index[i].name)
                }
                return index
            }
        },
        created() {
            if (this.$route.query.keyword) {
                this.form.keyword = this.$route.query.keyword;
            }

            this.$http({
                method: 'get',
                url: '/indices'
            })
            .then(response => {
                this.form.indices = response.data.body;
            })
            .catch(error => {
                this.form.indices.push('인덱스 목록 생성 실패')
                return error;
            })

            if (this.form.keyword !== '') {
                this.search();
            }
        },
        methods: {
            search() {
                /* eslint-disable no-console */
                if (this.form.keyword === '') {
                    return;
                }

                const parameter = `index=${this.searchIndex}&keyword=${this.form.keyword}&exceptedKeyword=${this.form.exceptedKeyword}&paging.page=${this.form.page}`

                this.document.documents = [];

                this.$http({
                    method: 'get',
                    url: `/search?${parameter}`
                })
                .then(response => {
                    const documents = response.data.body.documents
                    this.document.total = response.data.body.total

                    for (let i in documents) {
                        const document = documents[i].document

                        let j = 0
                        let row = {}
                        row.id = documents[i].documentId;
                        row.index = documents[i].index;
                        row.score = documents[i].score;

                        for (let col in document) {
                            const val = document[col];
                            const key = "col" + j

                            row[key] = val

                            j++
                        }
                        row.origin = document;
                        this.document.documents.push(row)
                    }

                    return response;
                })
                .catch(error => {
                    console.log(error);
                })
                /* eslint-enable no-console */
            },
            formSubmit() {
                this.form.page = 1;
                this.search();
            },
            pageChange() {
                this.search();
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
            <h4 class="card-title mb-4">Document 검색</h4>
            <form @submit.prevent="formSubmit" enctype="multipart/form-data">
              <div class="row">
                <div class="col-lg-6">
                  <div>
                    <div class="form-group mb-4">
                      <label for="index" class="card-title">인덱스</label>
                      <div>
                        <multiselect id="index" label="name" track-by="name" v-model="form.index" :options="form.indices"
                                     :multiple="true"></multiselect>
                      </div>
                    </div>
                  </div>
                </div>

              </div>
              <div class="row">
                <div class="col-lg-6">
                  <div class="form-group mb-4">
                    <label for="keyword" class="card-title">포함하는 단어</label>
                    <input
                        id="keyword"
                        type="text"
                        name="keyword"
                        v-model="form.keyword"
                        class="form-control"
                    />
                  </div>
                </div>

                <div class="col-lg-6">
                  <div class="form-group mb-4">
                    <label for="exceptedKeyword" class="card-title">제외하는 단어</label>
                    <input
                        id="exceptedKeyword"
                        type="text"
                        name="exceptedKeyword"
                        v-model="form.exceptedKeyword"
                        class="form-control"
                    />
                  </div>
                </div>
              </div>
              <!--              <div class="row">-->
              <!--                <div class="col-lg-12">-->
              <!--                  <div class="form-group">-->
              <!--                    <b-form-checkbox v-model="form.exactly" switch class="mb-1">-->
              <!--                      <label class="card-title">정확히 일치</label>-->
              <!--                    </b-form-checkbox>-->
              <!--                  </div>-->
              <!--                </div>-->
              <!--              </div>-->
              <button class="btn btn-primary" type="submit">검색 시작</button>
            </form>
          </div>
        </div>
      </div>
    </div>


    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <h4 class="card-title">Document 검색 결과</h4>

            <div class="table-responsive">
              <table class="table mb-0">
                <thead>
                <tr>
                  <th>#</th>
                  <th>Index</th>
                  <th>Score</th>
                  <th>Col0</th>
                  <th>Col1</th>
                  <th>Col2</th>
                  <th>Col3</th>
                  <th>Col4</th>
                  <th>원본</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(v, i) in document.documents" v-bind:key="v.id">
                  <th scope="row">{{i + 1 + (form.page - 1) * 10}}</th>
                  <td>{{v.index}}</td>
                  <td>{{v.score}}</td>
                  <td>{{v.col0}}</td>
                  <td>{{v.col1}}</td>
                  <td>{{v.col2}}</td>
                  <td>{{v.col3}}</td>
                  <td>{{v.col4}}</td>
                  <td>
                    <i @click="document.showOrigin=true; document.origin=v.origin" v-b-modal.modal-orign
                       class="mdi mdi-dots-horizontal font-size-18"></i>
                  </td>
                </tr>
                </tbody>
              </table>

              <b-modal v-model="document.showOrigin" size="lg" title="Document 원본" centered hide-footer>
                <p>
                  <vue-json-pretty
                      :data="document.origin"
                  >
                  </vue-json-pretty>
                </p>
              </b-modal>
            </div>
          </div>
        </div>
        <b-pagination
            prev-text="이전"
            next-text="다음"
            align="center"
            v-model="form.page"
            :total-rows="document.total"
            :per-page="10"
            @input="pageChange"></b-pagination>
      </div>

    </div>
    <!-- end row -->
  </Layout>
</template>
