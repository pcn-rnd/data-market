<script>
    import Layout from "../../layouts/auth";
    import appConfig from "@/app.config";

    /**
     * Login component
     */
    export default {
        page: {
            title: "로그인",
            meta: [{name: "description", content: appConfig.description}]
        },
        components: {Layout},
        data() {
            return {
                id: "",
                secret: "",
                authError: null,
                tryingToLogIn: false,
                isAuthError: false
            };
        },
        methods: {
            // Try to log the user in with the username
            // and password they provided.
            login() {
                this.tryingToLogIn = true;

                /* eslint-disable no-console */
                this.authError = null;
                return (
                    this.$http({
                        method: 'post',
                        url: '/auth/login',
                        data: {
                            username: this.id,
                            password: this.secret
                        }
                    })
                    .then(response => {
                        this.tryingToLogIn = false;

                        if (response.data.result === 'success') {
                            this.isAuthError = false;

                            this.$store.commit('SETTOKEN', response.data.body.token);
                            this.$store.commit('SETLOGINSTATE', true);
                            this.$store.commit('SETCURUSER', response.data.body.userId);

                            this.$router.push(
                                this.$route.query.redirectFrom || {name: "index"}
                            );
                        } else {
                            this.isAuthError = true;
                            this.authError = response.data.body;
                        }
                    })
                    .catch(error => {
                        this.tryingToLogIn = false;
                        this.authError = error ? error : "";
                        this.isAuthError = true;
                    })
                );

                /* eslint-enable no-console */
            }
        }
    };
</script>

<template>
  <Layout>
    <div class="row justify-content-center">
      <div class="col-md-8 col-lg-6 col-xl-5">
        <div class="card overflow-hidden">
          <div class="bg-soft-primary">
            <div class="row">
              <div class="col-7">
                <div class="text-primary p-4">
                  <h5 class="text-primary">Welcome Data Market Site!!</h5>
                  <p>로그인 하세요</p>
                </div>
              </div>
              <div class="col-5 align-self-end">
                <img src="@/assets/images/profile-img.png" alt class="img-fluid"/>
              </div>
            </div>
          </div>
          <div class="card-body pt-0">
            <div>
              <router-link tag="a" to="/">
                <div class="avatar-md profile-user-wid mb-4">
                                    <span class="avatar-title rounded-circle bg-light">
                                        <img src="@/assets/images/logo.svg" alt height="34"/>
                                    </span>
                </div>
              </router-link>
            </div>

            <b-alert v-model="isAuthError" variant="danger" class="mt-3" dismissible>{{authError}}</b-alert>

            <b-form class="p-2" @submit.prevent="login">
              <b-form-group id="input-group-1" label="id" label-for="input-1">
                <b-form-input id="input-1" v-model="id" type="text" placeholder="Enter ID"></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-2" label="Password" label-for="input-2">
                <b-form-input
                    id="input-2"
                    v-model="secret"
                    type="password"
                    placeholder="Enter password"
                ></b-form-input>
              </b-form-group>
              <!--<div class="custom-control custom-checkbox">
                <input id="customControlInline" type="checkbox" class="custom-control-input"/>
                <label class="custom-control-label" for="customControlInline">Remember me</label>
              </div>-->
              <div class="mt-3">
                <b-button type="submit" variant="primary" class="btn-block">Log In</b-button>
              </div>
              <!--<div class="mt-4 text-center">
                <router-link tag="a" to="/forgot-password" class="text-muted">
                  <i class="mdi mdi-lock mr-1"></i> Forgot your password?
                </router-link>
              </div>-->
            </b-form>
          </div>
          <!-- end card-body -->
        </div>
        <!-- end card -->

        <div class="mt-5 text-center">
          <!--
          <p>
              Don't have an account ?
              <router-link tag="a" to="/register" class="font-weight-medium text-primary">Signup now</router-link>
          </p>
          -->
          <p>
            © Since 1999 PCN
            <i class="mdi mdi-heart text-danger"></i>
          </p>
        </div>
        <!-- end row -->
      </div>
      <!-- end col -->
    </div>
    <!-- end row -->
  </Layout>
</template>

<style lang="scss" module></style>
