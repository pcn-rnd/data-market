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
      title: "Modify Dataset",
      meta: [{name: "description", content: appConfig.description}]
    },
    components: {Layout, PageHeader},
    data() {
      return {
        title: "Modify Dataset",
        items: [
          {
            text: "Dataset",
            href: "/"
          },
          {
            text: "Modify Dataset",
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
        urlsInfo :[
            {id: 1}
        ]
      };
    },
    created() {

    },
    methods: {
        createWs() {
          axios.post('/wss', this.workspace
          ).then(response => {
            if(response.status == 200){
              this.$router.push({path : '/'})
            }
          })
        },
        addUrlInfo() {
            this.urlsInfo.push({datatype : "", description : ""})
        },
        deleteInfo(index) {
            this.urlsInfo.splice(index, 1);
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
                        <label for="projectdesc" class="col-form-label col-lg-2">Category</label>
                        <b-dropdown size="sm" class="ml-3 mt-1">
                          <template v-slot:button-content>
                            카테고리 선택
                            <i class="mdi mdi-chevron-down"></i>
                          </template>
                          <b-dropdown-item-button>Action</b-dropdown-item-button>
                          <b-dropdown-item-button>Another action</b-dropdown-item-button>
                          <b-dropdown-item-button>Something else here</b-dropdown-item-button>
                        </b-dropdown>
                      </div>
                            <div class="form-group row mb-4">
                        <label for="projectname" class="col-form-label col-lg-2">Dataset Name</label>
                        <div class="col-lg-10">
                          <input
                              id="projectname"
                              name="projectname"
                              type="text"
                              class="form-control"
                              v-model="workspace.wsName"
                              placeholder="Enter Dataset Name..."
                          />
                        </div>
                      </div>
                            <div class="form-group row mb-4">
                        <label for="projectdesc" class="col-form-label col-lg-2">Dataset Description</label>
                        <div class="col-lg-10" >
                          <textarea
                              id="projectdesc"
                              class="form-control"
                              rows="10"
                              v-model="workspace.wsDs"
                              placeholder="Enter Dataset Description..."
                          ></textarea>
                        </div>
                      </div>
                            <div class="form-group row mb-4">
                                <label for="projectdesc" class="col-form-label col-lg-2 font-weight-bold">데이터셋 공개여부</label>
                                <div class="custom-control custom-radio mt-2 ml-3">
                                    <input
                                            type="radio"
                                            id="customRadio1"
                                            name="customRadio"
                                            class="custom-control-input"
                                    />
                                    <label class="custom-control-label" for="customRadio1">공개</label>
                                </div>
                                <div class="custom-control custom-radio mt-2 ml-3">
                                    <input
                                            type="radio"
                                            id="customRadio2"
                                            name="customRadio"
                                            class="custom-control-input"
                                    />
                                    <label class="custom-control-label" for="customRadio2">비공개</label>
                                </div>
                            </div>
                            <div class="form-group row mb-4">
                                <label for="projectdesc" class="col-form-label col-lg-2 font-weight-bold">요청 URL 정보</label>

                                <table class="table table-bordered ml-3" style="width: 70%;">
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
                                        <tr v-for="(info, index) in urlsInfo" :key="info.id">
                                            <td>
                                                <b-dropdown  variant="link">
                                                    <template v-slot:button-content>
                                                        선택
                                                        <i class="mdi mdi-chevron-down"></i>
                                                    </template>
                                                    <b-dropdown-item-button>PDF</b-dropdown-item-button>
                                                    <b-dropdown-item-button>CSV</b-dropdown-item-button>
                                                    <b-dropdown-item-button>TEXT</b-dropdown-item-button>
                                                </b-dropdown>

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


                            <div class="row justify-content-end">
                                <div class="col-lg-10 text-sm-right button-items">
                                    <b-button class="btn btn-success" @click="createWs()">Create</b-button>
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
