<script>
  import Layout from "../../layouts/main";
  import PageHeader from "@/components/page-header";
  import appConfig from "@/app.config";
  //import Switches from "vue-switches";
  import axios from "axios"


  /**
   * Workspace component
   */
  export default {
    page: {
      title: "데이터셋 검색",
      meta: [{name: "description", content: appConfig.description}]
    },
    components: {Layout,  PageHeader},
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
        datasetInfo: {},
        dataset : {},
        categories : {},
        populars : {},
        newest: {},
        defaultPgSize : 10,
        enabled: [],
        q : ""
      };
    },
    computed: {

    },
    created() {
      this.getCats();
      this.getPopulars();
      this.getNewest();
      this.getDsList(null);

      console.log(this.$store.state)
    },
    methods: {
      getCats() {
        axios.get('/cat', {
        }).then(response => {
          this.categories = response.data.body;
        })
      },
      getPopulars() {
        axios.get('/ds/populars/5', {
        }).then(response => {
          this.populars = response.data.body;
        })
      },
      getNewest() {
        axios.get('/ds/newest', {
        }).then(response => {
          this.newest = response.data.body;
        })
      },
      getDsList(q) {
        /*console.log("q = " + this.q);*/
        if(q == null) q = '';
        axios.get(`/ds?q=${q}&size=${this.defaultPgSize}&page=${this.datasetInfo.number-1}`,{

        }).then(response => {
          console.log(response.data.body);
          this.datasetInfo = response.data.body;
          this.dataset = this.datasetInfo.content;
          this.datasetInfo.number += 1;
        })
      },
      isLogin() {
        console.log(this.$store.state.isLogin);
        return this.$store.state.isLogin;
      }
    }
  };
</script>

<template>
  <Layout>
    <div class="row">
      <div class="col-12">
        <div class="mb-4">
          <div class="font-size-13 font-weight-bold col-8 d-inline-block">
            <span >인기 데이터셋</span>
            <b-dropdown size="sm" class="m-2" variant="link">
              <template v-slot:button-content >
                1. {{populars[0] ? populars[0].title : ''}}
                <i class="mdi mdi-chevron-down"></i>
              </template>
              <span v-for="(item, idx) in populars" :key="idx">
                <router-link :to="{ name: 'Dataset Detail', query: { setId: item.setId }}" role="menuitem">
                  <b-dropdown-item-button>{{idx+1}}. {{item.title}}</b-dropdown-item-button>
                </router-link>
              </span>
            </b-dropdown>
          </div>


          <div class="font-size-13 font-weight-bold d-inline-block">
            <span >최신 데이터셋</span>
            <b-dropdown size="sm" class="m-2" variant="link">
              <template v-slot:button-content >
                1. {{newest[0] ? newest[0].title : ''}}
                <i class="mdi mdi-chevron-down"></i>
              </template>
              <span v-for="(item, idx) in newest" :key="idx">
                <router-link :to="{ name: 'Dataset Detail', query: { setId: item.setId }}" role="menuitem">
                  <b-dropdown-item-button>{{idx+1}}. {{item.title}}</b-dropdown-item-button>
                </router-link>
              </span>
            </b-dropdown>
          </div>
        </div>
      </div>
    </div>


    <div class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <div class="search-box col-1 mr-3 d-inline-block">
              <div class="position-relative">

                <div class="form-group row mb-0">
                  <div class="col-md-10">

                    <select class="custom-select"  style="border: none; width: 150%">
                      <option selected> 카테고리 </option>
                      <option value="1" v-for="(item, idx) in categories" :key="idx">{{item.catName}}</option>
                    </select>
                  </div>
                </div>


               <!-- <b-dropdown variant="link">
                  <template slot="button-content">
                    카테고리
                    <i class="mdi mdi-chevron-down"></i>
                  </template>



                  <span v-for="(item, idx) in categories" :key="idx">
                    <b-dropdown-item>{{item.catName}}</b-dropdown-item>
                  </span>
                </b-dropdown>-->
              </div>
            </div>

            <div class="search-box col-9 d-inline-block" v-on:keydown.enter="getDsList(q)">
              <div class="position-relative">
                <input type="text" class="form-control"  placeholder="Search..." v-model="q" style="width: 120%" />
                <i class="bx bx-search-alt search-icon"></i>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>


    <PageHeader :title="title"/>
      <div class="row">
        <div class="col-lg-12">
          <div class="text-sm-right">
            <div class="float-left">
              <span class="font-size-20 font-weight-bold">결과(<span>{{datasetInfo.totalElements}}</span>)</span>
                <b-dropdown variant="link">
                  <template slot="button-content">
                    정확도 순
                    <i class="mdi mdi-chevron-down"></i>
                  </template>
                  <b-dropdown-item>최신순</b-dropdown-item>
                  <b-dropdown-item>조회수 순</b-dropdown-item>
                </b-dropdown>
            </div>
          </div>
          <router-link tag="a" to="/create" class="btn btn-success btn-rounded mb-2 mr-2 float-right" v-if="isLogin()">
              <i class="mdi mdi-plus mr-1" ></i> 새 데이터셋 생성
          </router-link>
        </div>
    </div>

    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="table-responsive">
            <table class="table mb-0">
              <thead>
              <tr>
                <th>카테고리</th>
                <th>제목</th>
                <th>데이터 형태</th>
                <th>날짜</th>
                <th>조회수</th>
              </tr>
              </thead>
              <tbody v-for="(data, idx) in dataset" :key="idx">
                <tr>
                  <td>{{data.catId.catName}}</td>
                  <td>
                    <router-link :to="{ name: 'Dataset Detail', query: { setId: data.setId }}" role="menuitem">
                      <span class="font-weight-bold" style="color: #495057;">{{data.title}}</span>
                    </router-link>
                  </td>
                  <td v-for="(type, i) in data.datatype.split(',')" :key="i">
                    <b-badge pill variant="primary">{{type}}</b-badge>
                    <!--<b-badge pill variant="success" class="ml-1">CSV</b-badge>-->
                  </td>
                  <td>{{$moment(data.crtDt).format('YYYY-MM-DD')}}</td>
                  <td>{{data.views}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>


        <b-pagination
            prev-text="이전"
            next-text="다음"
            align="center"
            v-model="datasetInfo.number"
            :total-rows="datasetInfo.totalElements"
            :per-page="10"
            @input="getDsList()"></b-pagination>
      </div>
    </div>
    <!-- end row -->
  </Layout>
</template>
