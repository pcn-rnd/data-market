<script>
    import Layout from "../../layouts/main";
    import PageHeader from "@/components/page-header";
    import appConfig from "@/app.config";
    import axios from "axios"
    //import VueJsonPretty from 'vue-json-pretty'
    import { projectData } from "./../projects/data-projects";
    import MyPageNav from "./my-page-nav";

    /**
     * dataset list what user purchased component
     */
    export default {
        page: {
            title: "Purchase List",
            meta: [{name: "description", content: appConfig.description}]
        },
        components: {Layout, PageHeader, MyPageNav},
        data() {

            return {
                projectData: projectData,
                title: "Purchase List",
                items: [
                    {
                        text: "My Page",
                        href: "/"
                    },
                    {
                        text: "구매 목록 조회",
                        active: true
                    }
                ],
                list : {},
                defaultPgSize : 10
            };
        },
        created() {
            this.getPl();
        },
        methods: {
            getPl() {
                axios.get(`pl/${this.$store.state.curUser}?size=${this.defaultPgSize}&page=${this.list.number-1}`)
                    .then(res => {
                        console.log(this.list = res.data.body);
                        this.list.number += 1;
                    });
            }
        }
    };
</script>

<template>
    <Layout>
        <PageHeader :title="title" :items="items"/>
        <div class="row">
             <MyPageNav />

            <div class="col-lg-9">
                <div class="row">
                    <div>

                    </div>
                    <div v-for="(item, idx) in list.content" :key="idx" class="col-xl-6 col-sm-6">
                        <div class="card" >
                            <div class="card-body">
                                <div class="media">
                                    <div class="media-body overflow-hidden">
                                        <h5 class="text-truncate font-size-15">
                                            <router-link :to="{ name: 'Dataset Detail', query: { setId: item.setId.setId }}" role="menuitem">
                                                <a href="#" class="text-dark">{{item.setId.title ? item.setId.title : ''}}</a>
                                            </router-link>
                                        </h5>
                                        <p class="text-muted mb-4">{{item.setId.content}}</p>

                                    </div>
                                </div>
                            </div>
                            <div class="px-4 py-3 border-top">
                                <ul class="list-inline mb-0">
                                    <li class="list-inline-item mr-3 float-right">
                                        <span class="badge badge-primary" v-if="item.ableYn === 'Y'">
                                            유효
                                        </span>
                                        <span class="badge badge-light" v-else-if="item.ableYn === 'N'">
                                            만료
                                        </span>
                                        <span class="badge badge-danger" v-else=""></span>
                                    </li>
                                    <li v-b-tooltip.hover.top class="list-inline-item mr-3">
                                        <i class="bx bx-calendar mr-1"></i>
                                        {{$moment(item.setId.crtDt).format('YYYY-MM-DD')}}
                                    </li>
                                    <!--<li v-b-tooltip.hover.top class="list-inline-item mr-3" title="Comments">
                                        <i class="bx bx-comment-dots mr-1"></i>

                                        {{grid.comment}}
                                    </li>-->
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <b-pagination
                        prev-text="이전"
                        next-text="다음"
                        align="center"
                        v-model="list.number"
                        :total-rows="list.totalElements"
                        :per-page="defaultPgSize"
                        @input="getPl"
                ></b-pagination>



            </div>

        </div>

    </Layout>
</template>
