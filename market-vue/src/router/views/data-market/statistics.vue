<script>
    import Layout from "../../layouts/main";
    import PageHeader from "@/components/page-header";
    import appConfig from "@/app.config";
    //import Switches from "vue-switches";
    import axios from "axios"
    import CountByCat from "./charts/CountByCat";

    /**
     * Workspace component
     */
    export default {
        page: {
            title: "데이터셋 검색",
            meta: [{name: "description", content: appConfig.description}]
        },
        components: {Layout, CountByCat},
        data() {
            return {
                title: "데이터셋 검색 및 조회",
                items: [
                    {
                        text: "Home",
                        href: "/"
                    },
                    {
                        text: "Search" ,
                        active: true
                    }
                ],
                most : {},
                cnts : {
                    total : 0,
                    ycnt : 0
                },
                populars : {}

            };
        },
        computed: {

        },
        created() {
            this.getMost();
            this.getAllCnt();
            this.getPubCnt();
            this.getPopulars();
        },
        methods: {
            getCats() {
                axios.get('/cat', {
                }).then(response => {

                })
            },
            getMost() {
                axios.get('/ds/most').then(res => {
                    this.most = res.data.body;
                })
            },
            getPopulars() {
                axios.get('/ds/populars/10', {
                }).then(response => {
                    this.populars = response.data.body;
                    console.log(this.populars)
                })
            },
            getAllCnt() {
                axios.get('/ds/count').then(res => {
                    this.cnts.total = res.data.body;
                })
            },
            getPubCnt() {
                axios.get('/ds/ycnt').then(res => {
                    this.cnts.ycnt = res.data.body;
                })
            }

        }
    };
</script>

<template>
    <Layout>
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title mb-4">Polar area Chart</h4>
                        <div class="row text-center">
                            <div class="col-sm-4">
                                <h5 class="mb-0">{{cnts.total}}</h5>
                                <p class="text-muted">Total</p>
                            </div>
                            <div class="col-sm-4">
                                <h5 class="mb-0">{{cnts.ycnt}}</h5>
                                <p class="text-muted">Public</p>
                            </div>
                            <div class="col-sm-4">
                                <h5 class="mb-0">85412</h5>
                                <p class="text-muted">Deactivated</p>
                            </div>
                        </div>
                        <!-- Polar area Chart -->
                        <CountByCat :height="160" />
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title mb-4">인기 데이터셋</h4>
                        <div class="table-responsive mb-0">
                            <table class="table table-nowrap table-centered">
                                <tbody>
                                <tr v-for="(item, idx) in populars" :key="idx">
                                    <td style="width: 60px;">
                                        <div class="custom-control custom-checkbox">
                                            <span class="font-weight-bold">{{idx+1}}</span>
                                        </div>
                                    </td>
                                    <td>
                                        <h5 class="text-truncate font-size-14 m-0 font-weight-bold">
                                            <router-link :to="{ name: 'Dataset Detail', query: { setId: item.setId }}" role="menuitem">
                                                <a href="javascript: void(0);" class="text-dark">{{item.title}}</a>
                                            </router-link>
                                        </h5>
                                    </td>

                                </tr>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title mb-4">가장 많이 구매한 데이터셋</h4>
                        <div class="table-responsive mb-0">
                            <table class="table table-nowrap table-centered">
                                <tbody>
                                <tr v-for="(item, idx) in most" :key="idx">
                                    <td style="width: 60px;">
                                        <div class="custom-control custom-checkbox">
                                            <span class="font-weight-bold">{{idx+1}}</span>
                                        </div>
                                    </td>
                                    <td>
                                        <h5 class="text-truncate font-size-14 m-0 font-weight-bold">
                                            <router-link :to="{ name: 'Dataset Detail', query: { setId: item.setId }}" role="menuitem">
                                                <a href="javascript: void(0);" class="text-dark">{{item.title}}</a>
                                            </router-link>
                                        </h5>
                                    </td>

                                </tr>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end row -->
    </Layout>
</template>
