<script>
    import Layout from "../layouts/main";
    import appConfig from "@/app.config";

    /**
     * Dashboard Component
     */
    export default {
        page: {
            title: "Dashboard",
            meta: [{name: "description", content: appConfig.description}]
        },
        components: {Layout},
        created() {
            this.resetStats();
            this.resetTrend(this.trend.range);
        },
        data() {
            return {
                title: "Dashboard",
                stats: {
                    all: {
                        primaries: {
                            docs: {}
                        }
                    },
                    indices: [],
                    current: []
                },
                trend: {
                    range: 7,
                    data: []
                }
            };
        },
        methods: {
            resetTrend(range) {
                this.trend.range = range;

                this.$http({
                    method: 'get',
                    url: `/popularWords?range=${this.trend.range}`
                })
                .then(response => {
                    this.trend.data = response.data.body.documents;
                })
                .catch(error => {
                    return error;
                })
            },
            resetStats() {
                this.$http({
                    method: 'get',
                    url: '/stats'
                })
                .then(response => {
                    this.stats = response.data.body;
                })
                .catch(error => {
                    return error;
                })
            },
            rebuild(index) {
                this.$http({
                    method: 'put',
                    url: `/index/${index}`
                })
                .then(response => {
                    alert(response.data.body.index + " 색인 작업을 시작 합니다");
                })
                .catch(error => {
                    return error;
                })
            }
        }
    };
</script>

<template>
  <Layout>
    <!-- start page title -->
    <div class="row">
      <div class="col-12">
        <div class="page-title-box d-flex align-items-center justify-content-between">
          <h4 class="mb-0 font-size-18">Dashboard</h4>

          <div class="page-title-right">
            <ol class="breadcrumb m-0">
              <li class="breadcrumb-item active">Welcome to Mask Dashboard</li>
            </ol>
          </div>
        </div>
      </div>
    </div>
    <!-- end page title -->
    <div class="row">
      <div class="col-xl-4">
        <div class="card">
          <div class="card-body">
            <h4 class="card-title mb-4">색인 현황</h4>
            <div class="text-center">
              <div class="avatar-sm mx-auto mb-4">
                <span class="avatar-title rounded-circle bg-primary font-size-18">
                  <i class="bx bx-copy-alt font-size-24"></i>
                </span>
              </div>
              <p class="font-16 text-muted mb-2"></p>
              <h5>
                <a href="javascript: void(0);" class="text-dark">
                  <span class="text-muted font-16">Total</span>
                  {{stats.all.primaries.docs.count}}
                  <span class="text-muted font-16">documents</span>
                </a>
              </h5>
            </div>
            <div class="row mt-4">
              <div class="col-sm-6" v-for="index in stats.indices" :key="index.name">
                <div class="social-source text-center mt-3">
                  <h5 class="font-size-15">{{index.name}}</h5>
                  <a href="javascript: void(0);" @click.prevent="rebuild(index.name)">
                    <span class="badge badge-pill badge-soft-success font-size-12 badge-soft-danger">인덱스 재생성</span>
                  </a>
                  <p class="text-muted mb-0">{{index.primaries.docs.count}} documents</p>
                  <p class="text-muted mb-0">{{index.primaries.store.size_in_bytes}} bytes</p>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
      <!-- end col -->
      <div class="col-xl-4">
        <div class="card">
          <div class="card-body">
            <div class="float-right">
              <ul class="nav nav-pills">
                <li class="nav-item">
                  <a class="nav-link" :class="{active: trend.range === 7}" href="javascript: void(0);" @click.prevent="resetTrend(7)">Week</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" :class="{active: trend.range === 30}" href="javascript: void(0);" @click.prevent="resetTrend(30)">Month</a>
                </li>
              </ul>
            </div>
            <h4 class="card-title mb-4">검색어 트렌드</h4>

            <div class="table-responsive mt-4">
              <table class="table table-centered">
                <tbody>
                <tr v-for="document in trend.data" :key="document.key">
                  <td style="width: 180px">
                    <p class="mb-0"><strong>{{document.key}}</strong></p>
                  </td>
                  <td style="width: 80px">
                    <p class="mb-0">{{document.hits}}</p>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <!-- end col -->
      <div class="col-xl-4">
        <div class="card">
          <div class="card-body">
            <h4 class="card-title mb-5">작업 현황</h4>
            <ul class="verti-timeline list-unstyled">
              <li class="event-list">
                <div class="event-timeline-dot">
                  <i class="bx bx-right-arrow-circle font-size-18"></i>
                </div>
                <div class="media">
                  <div class="mr-3">
                    <h5 class="font-size-14">
                      22 Nov
                      <i
                          class="bx bx-right-arrow-alt font-size-16 text-primary align-middle ml-2"
                      ></i>
                    </h5>
                  </div>
                  <div class="media-body">
                    <div>Responded to need “Volunteer Activities</div>
                  </div>
                </div>
              </li>
              <li class="event-list">
                <div class="event-timeline-dot">
                  <i class="bx bx-right-arrow-circle font-size-18"></i>
                </div>
                <div class="media">
                  <div class="mr-3">
                    <h5 class="font-size-14">
                      17 Nov
                      <i
                          class="bx bx-right-arrow-alt font-size-16 text-primary align-middle ml-2"
                      ></i>
                    </h5>
                  </div>
                  <div class="media-body">
                    <div>
                      Everyone realizes why a new common language would be desirable...
                      <a
                          href="javascript: void(0);"
                      >Read more</a>
                    </div>
                  </div>
                </div>
              </li>
              <li class="event-list active">
                <div class="event-timeline-dot">
                  <i class="bx bxs-right-arrow-circle font-size-18 bx-fade-right"></i>
                </div>
                <div class="media">
                  <div class="mr-3">
                    <h5 class="font-size-14">
                      15 Nov
                      <i
                          class="bx bx-right-arrow-alt font-size-16 text-primary align-middle ml-2"
                      ></i>
                    </h5>
                  </div>
                  <div class="media-body">
                    <div>Joined the group “Boardsmanship Forum”</div>
                  </div>
                </div>
              </li>
              <li class="event-list">
                <div class="event-timeline-dot">
                  <i class="bx bx-right-arrow-circle font-size-18"></i>
                </div>
                <div class="media">
                  <div class="mr-3">
                    <h5 class="font-size-14">
                      12 Nov
                      <i
                          class="bx bx-right-arrow-alt font-size-16 text-primary align-middle ml-2"
                      ></i>
                    </h5>
                  </div>
                  <div class="media-body">
                    <div>Responded to need “In-Kind Opportunity”</div>
                  </div>
                </div>
              </li>
            </ul>
            <div class="text-center mt-4">
              <a href="javascript: void(0);" class="btn btn-primary btn-sm">Load More</a>
            </div>
          </div>
        </div>
      </div>
      <!-- end col -->

    </div>
    <!-- end row -->

  </Layout>
</template>
