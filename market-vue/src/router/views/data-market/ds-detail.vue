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
            title: "데이터셋 정보 조회",
            meta: [{name: "description", content: appConfig.description}]
        },
        components: {Layout, PageHeader},
        data() {
            return {
                title: "데이터셋 상세정보 조회",
                items: [
                    {
                        text: "Search",
                        href: "/"
                    },
                    {
                        text: "데이터셋 상세정보 조회",
                        active: true
                    }
                ],
                dataset : {},
                urlsInfo :[
                    {id: 1}
                ],
                curUser : ""
            };
        },
        created() {
          if (this.$store.state.curUser) {
            this.curUser = this.$store.state.curUser;
          }

          this.getDetails();
        },
        methods: {

            getDetails() {
                axios.get(`/ds/${this.$route.query.setId}`,{

                }).then(response => {
                    this.dataset = response.data.body;
                    this.dataset.urlsInfo = JSON.parse(this.dataset.urlsInfo);
                    console.log(this.dataset);
                })
            },
            isLogin() {
                console.log(this.$store.state);
                return this.curUser === this.dataset.seller.userId;
            },
            deleteDs() {
                axios.delete(`/ds/${this.$route.query.setId}`).then(res => {
                    if(res.status === 200) {
                        alert("삭제 되었습니다.");
                        this.$router.push({path: '/'})
                    }
                })
            }

        },
        filters: {
            numberComma(val){
                return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }
        }
    };
</script>

<template>
    <Layout>
        <PageHeader :title="title" :items="items"/>
        <div class="row">
            <div class="col-xl-8">
                <div class="card">
                    <div class="card-body">

                            <div class="mt-3">
                                <a href="#" class="text-primary">{{dataset.catId ? dataset.catId.catName : ''}}</a>
                                <h4 class="mt-1 mb-3 font-weight-bold">{{dataset.title}}</h4>



                                <p class="text-muted mb-4">
                                    {{dataset.content}}
                                </p>

                                <h5 class="mt-1 mb-3 font-weight-bold">제공 형태</h5>


                                <table class="table table-bordered w-75">
                                    <thead>
                                    <tr>
                                        <th>데이터 형식</th>
                                        <th>설명</th>

                                    </tr>
                                    </thead>

                                    <tbody v-for="(info, idx) in dataset.urlsInfo" :key="idx">
                                        <tr>
                                            <td>
                                                {{info.datatype}}
                                            </td>
                                            <td>
                                                {{info.description}}
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>



                                <!--<div class="row mb-3">
                                    <div class="col-md-6">
                                        <div>
                                            <p class="text-muted">
                                                <i class="bx bx-unlink font-size-16 align-middle text-primary mr-1"></i> Wireless
                                            </p>
                                            <p class="text-muted">
                                                <i class="bx bx-shape-triangle font-size-16 align-middle text-primary mr-1"></i> Wireless Range : 10m
                                            </p>
                                            <p class="text-muted">
                                                <i class="bx bx-battery font-size-16 align-middle text-primary mr-1"></i> Battery life : 6hrs
                                            </p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div>
                                            <p class="text-muted">
                                                <i class="bx bx-user-voice font-size-16 align-middle text-primary mr-1"></i> Bass
                                            </p>
                                            <p class="text-muted">
                                                <i class="bx bx-cog font-size-16 align-middle text-primary mr-1"></i> Warranty : 1 Year
                                            </p>
                                        </div>
                                    </div>
                                </div>-->


                        </div>


                        <div class="row mt-4">

                        </div>
                        <!-- end row-->
                    </div>
                </div>
            </div>
            <div class="col-xl-4">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title mb-3">가격 정보</h4>

                        <div class="table-responsive">
                            <table class="table mb-0">

                                <h5 class="mb-2">
                                    Price : <b>₩{{dataset.price | numberComma}}</b>
                                </h5>
                                <h6 class="mb-2">
                                    판매자 : <b>{{dataset.seller ? dataset.seller.userName : ''}}</b>
                                </h6>

                            </table>
                            <router-link v-if="this.curUser != '' && !isLogin()" :to="{ name: 'Purchase Dataset', query: { setId: dataset.setId }}" role="menuitem">
                                <b-button pill class="btn btn-success w-100">구매하기</b-button>
                            </router-link>
                            <div class="mt-2 mb-2 button-items" v-if=isLogin()>
                                <router-link :to="{ name: 'Dataset Modify', query: { setId: dataset.setId }}" role="menuitem">
                                    <b-button pill class="btn btn-warning col-6 mr-1">수정</b-button>
                                </router-link>

                                <b-button pill class="btn btn-danger col-5 ml-1" @click="deleteDs">삭제</b-button>
                            </div>
                        </div>
                        <!-- end table-responsive -->
                    </div>
                </div>
                <!-- end card -->
            </div>
        </div>

    </Layout>
</template>
