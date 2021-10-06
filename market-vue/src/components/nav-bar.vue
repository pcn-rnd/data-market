<script>
    import router from "@/router";

    /**
     * Nav-bar Component
     */

    export default {
        data() {
            return {
                autoComplete: {
                    result: []
                },
                popular: {
                    result: []
                }
            }
        },
        computed: {
            autoCompleteVisible() {
                return this.autoComplete.result.length + this.popular.result.length > 0;
            }
        },
        methods: {
            toggleMenu() {
                this.$parent.toggleMenu();
            }
            ,
            toggleRightSidebar() {
                this.$parent.toggleRightSidebar();
            }
            ,
            initFullScreen() {
                document.body.classList.toggle("fullscreen-enable");
                if (
                    !document.fullscreenElement &&
                    /* alternative standard method */ !document.mozFullScreenElement &&
                    !document.webkitFullscreenElement
                ) {
                    // current working methods
                    if (document.documentElement.requestFullscreen) {
                        document.documentElement.requestFullscreen();
                    } else if (document.documentElement.mozRequestFullScreen) {
                        document.documentElement.mozRequestFullScreen();
                    } else if (document.documentElement.webkitRequestFullscreen) {
                        document.documentElement.webkitRequestFullscreen(
                            Element.ALLOW_KEYBOARD_INPUT
                        );
                    }
                } else {
                    if (document.cancelFullScreen) {
                        document.cancelFullScreen();
                    } else if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen();
                    } else if (document.webkitCancelFullScreen) {
                        document.webkitCancelFullScreen();
                    }
                }
            },
            executeAutoComplete(event) {
                /* eslint-disable no-console */
                if (event.target.value.length < 2) {
                    this.autoComplete.result = [];
                    return;
                }

                this.$http({
                    method: 'get',
                    url: `/assistantWords?keyword=${event.target.value}`
                })
                .then(response => {
                    this.autoComplete.result = response.data.body.documents;
                    return response;
                })
                .catch(error => {
                    console.log(error);
                })
                /* eslint-enable no-console */
            },
            search() {
                const keyword = document.getElementById("topSearchKeyword").value;

                if (keyword) {

                    router.push({
                        name: 'Documents', query: {keyword: keyword}
                    })
                }
            },
            logout() {
                this.$store.commit('SETTOKEN', null);
                this.$store.commit('SETLOGINSTATE', false);

                router.push({
                    name: 'login'
                })
            }
        }
    }
    ;
</script>

<template>
  <header id="page-topbar">
    <div class="navbar-header">
      <div class="d-flex">
        <!-- LOGO -->
        <div class="navbar-brand-box">
          <router-link tag="a" to="/" class="logo logo-dark">
                        <span class="logo-sm">
                            <img src="@/assets/images/logo.svg" alt height="22"/>
                        </span>
            <span class="logo-lg">
                            <img src="@/assets/images/logo-dark.png" alt height="17"/>
                        </span>
          </router-link>

          <router-link tag="a" to="/" class="logo logo-light">
                        <span class="logo-sm">
                          <img src="@/assets/images/logo-light.svg" alt height="22"/>
                        </span>
            <span class="logo-lg">
                            <img src="@/assets/images/logo-light.png" alt height="19"/>
                        </span>
          </router-link>
        </div>

        <button
            id="vertical-menu-btn"
            type="button"
            class="btn btn-sm px-3 font-size-16 header-item"
            @click="toggleMenu"
        >
          <i class="fa fa-fw fa-bars"></i>
        </button>

        <!-- App Search-->
        <form class="app-search d-none d-lg-block" @submit.prevent="search">
          <div class="position-relative">
            <input type="text" id="topSearchKeyword" class="form-control" @keyup="executeAutoComplete" placeholder="Search..."/>
            <span class="bx bx-search-alt"></span>
          </div>
          <div class="btn-group mr-1 mt-2" v-if="autoCompleteVisible">
            <ul role="menu" tabindex="-1" class="dropdown-menu show" aria-labelledby="__BVID__123__BV_toggle_"
                style="position: absolute; transform: translate3d(0px, 35px, 0px); top: 0px; left: 0px; will-change: transform;"
                x-placement="bottom-start">
              <li role="presentation" v-for="key in autoComplete.result" :key="key.document.name">
                <router-link :to="{ name: 'Documents', query: { keyword: key.document.name }}" role="menuitem"
                             target="_self" class="dropdown-item">
                  {{key.document.name}}
                </router-link>
              </li>
              <!--                            <li role="presentation">-->
              <!--                                <hr role="separator" aria-orientation="horizontal" class="dropdown-divider">-->
              <!--                            </li>-->
              <!--                            <li role="presentation" v-for="key in popular.result" :key="key.key">-->
              <!--                                <a role="menuitem" target="_self" href="javascript: void(0);" class="dropdown-item">-->
              <!--                                    {{key.key}}({{key.count}})-->
              <!--                                </a>-->
              <!--                            </li>-->
            </ul>
          </div>
        </form>

      </div>

      <div class="d-flex">
        <b-dropdown
            class="d-inline-block d-lg-none ml-2"
            variant="black"
            menu-class="dropdown-menu-lg p-0"
            toggle-class="header-item noti-icon"
            right
        >
          <template v-slot:button-content>
            <i class="mdi mdi-magnify"></i>
          </template>

          <form class="p-3">
            <div class="form-group m-0">
              <div class="input-group">
                <input
                    type="text"
                    class="form-control"
                    placeholder="Search ..."
                    aria-label="Recipient's username"
                />
                <div class="input-group-append">
                  <button class="btn btn-primary" type="submit">
                    <i class="mdi mdi-magnify"></i>
                  </button>
                </div>
              </div>
            </div>
          </form>
        </b-dropdown>

        <div class="dropdown d-none d-lg-inline-block ml-1">
          <button type="button" class="btn header-item noti-icon" @click="initFullScreen">
            <i class="bx bx-fullscreen"></i>
          </button>
        </div>

        <div class="dropdown d-none d-lg-inline-block ml-1">
          <button class="btn header-item noti-icon" @click="logout">
            <i class="bx bx-power-off"></i>
          </button>
        </div>
      </div>
    </div>
  </header>
</template>
