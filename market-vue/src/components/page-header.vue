<script>
    export default {
        components: {},
        props: {
            title: {
                type: String,
                default: '',
            },
            items: {
                type: Array,
                default: () => {
                    return []
                },
            },
        },
        methods: {
            back: function () {
                history.back();
            }
        }
    }
</script>

<template>
  <!-- start page title -->
  <div class="row">
    <div class="col-12">
      <div class="page-title-box d-flex align-items-center justify-content-between">
        <h4 class="mb-0 font-size-18">{{ title }}

          <!--          <button type="button" class="btn btn-sm" @click.prevent="back">-->
          <!--            <i class="bx bx-arrow-back font-size-16  mr-0"></i>-->
          <!--          </button>-->
        </h4>
        <div class="page-title-right">
          <b-breadcrumb :items="items" class="m-0"></b-breadcrumb>
        </div>
      </div>
    </div>
  </div>
  <!-- end page title -->
</template>
