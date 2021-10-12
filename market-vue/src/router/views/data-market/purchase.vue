<script>
    import Layout from "../../layouts/main";
    import PageHeader from "@/components/page-header";
    import appConfig from "@/app.config";
    import axios from "axios"
    //import VueJsonPretty from 'vue-json-pretty'
    import { projectData } from "./../projects/data-projects";
   // import MyPageNav from "./my-page-nav";

    /**
     * list of dataset maked by me
     */
    export default {
        page: {
            title: "구매하기",
            meta: [{name: "description", content: appConfig.description}]
        },
        components: {Layout, PageHeader},
        data() {
            return {
                projectData: projectData,
                title: "구매하기",
                items: [
                    {
                        text: "Dataset",
                        href: "/"
                    },
                    {
                        text: "구매하기",
                        active: false
                    }
                ],
                dataset : {},
                purchaseObj : {}
            };
        },
        created() {
            this.getDetails();
        },
        methods: {
            getDetails() {
                axios.get(`/ds/${this.$route.query.setId}`,{

                }).then(response => {
                    this.dataset = response.data.body;
                    this.dataset.content = this.dataset.content.substring(0, 25);
                    this.setPurchaseObj();

                    console.log(this.dataset);
                })
            },
            purchase() {
                axios.post('/pl/credit', this.purchaseObj)
                    .then(response => {
                        if(response.status === 200) {
                            alert("구매가 완료되었습니다.");
                            this.$router.push({path : '/'});
                        }
                    });
            },
            setPurchaseObj() {
                this.purchaseObj = {
                    "listId" : 0,
                    "ableYn" : "Y",
                    "purDt" : new Date(),
                    "setId" : {
                        "setId" : this.dataset.setId
                    },
                    "customer" : {
                        "userId" : "pcn" // 현재 사용자로 수정
                    }
                }
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
                        <div class="table-responsive">
                            <table class="table table-centered mb-0 table-nowrap">
                                <thead class="thead-light">
                                <tr>
                                    <th>상품정보</th>
                                    <th>판매자</th>
                                    <th>가격</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>

                                    <td style="width: 50%;">
                                        <h5 class="font-size-16 text-truncate font-weight-bold">
                                            <router-link
                                                    to="/ecommerce/product-detail"
                                                    class="text-dark"
                                            >{{dataset.title}}</router-link>
                                        </h5>
                                        <p class="mb-0">
                                            {{dataset.content}}...
                                        </p>
                                    </td>
                                    <td>{{dataset.seller ? dataset.seller.userName : ''}}</td>
                                    <td><span>₩{{dataset.price | numberComma}}</span></td>


                                </tr>

                                </tbody>
                            </table>
                        </div>
                        <div class="row mt-4">
                            <!--<div class="col-sm-6">
                                <router-link to="/ecommerce/product-detail" class="btn btn-secondary">
                                    <i class="mdi mdi-arrow-left mr-1"></i> Continue Shopping
                                </router-link>
                            </div>-->
                            <!-- end col -->
                           <!-- <div class="col-sm-12 float-right">
                                <div class="text-sm-right mt-2 mt-sm-0">
                                    <router-link to="/ecommerce/checkout" class="btn btn-success">
                                        <i class="mdi mdi-cart-arrow-right mr-1"></i> Checkout
                                    </router-link>
                                </div>
                            </div>-->
                            <!-- end col -->
                        </div>
                        <!-- end row-->
                    </div>
                </div>
            </div>
            <div class="col-xl-4">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title mb-3">Order Summary</h4>

                        <div class="table-responsive">
                            <table class="table mb-0">
                                <tbody>
                                <tr>
                                    <td>Cost :</td>
                                    <td>₩{{dataset.price | numberComma}}</td>
                                </tr>
                                <!--<tr>
                                    <td>Discount :</td>
                                    <td>- $ 157</td>
                                </tr>-->
                                <tr>
                                    <th>Total :</th>
                                    <th>₩{{dataset.price | numberComma}}</th>
                                </tr>
                                </tbody>
                            </table>
                            <router-link :to="{ name: 'Purchase Dataset', query: { setId: dataset.setId }}" role="menuitem">
                                <b-button pill class="btn btn-success w-100" @click="purchase">결제하기</b-button>
                            </router-link>
                        </div>
                        <!-- end table-responsive -->
                    </div>
                </div>
                <!-- end card -->
            </div>
        </div>

    </Layout>
</template>
