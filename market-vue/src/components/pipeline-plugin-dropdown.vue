<script>

    import Multiselect from "vue-multiselect";

    export default {
        components: {Multiselect},
        props: {
            index: Number,
            plugins: {
                type: Array,
                default: () => {
                    return []
                }
            },
            plugin: {},
            pluginType: {
                type: String,
                default: ''
            }
        },
        data: function () {
            return {
                type: ''
            }
        },
        /* eslint-disable no-console */
        methods: {}
    }
</script>

<template>
  <b-row class="mb-3 mt-4">
    <b-col md="11">
      <multiselect :id="'select-input-plugin-' + index" v-model="type" :options="plugins"
                   @input="$emit('get-plugin', pluginType, index, type)"></multiselect>
    </b-col>
    <b-dropdown right variant="white" class="float-right" toggle-class="p-0">
      <template slot="button-content">
        <i class="mdi mdi-dots-vertical m-0 text-muted h5"></i>
      </template>
      <b-dropdown-item href="javascript: void(0);" @click.prevent="plugin.hide = true" v-show="plugin.type !== '' && !plugin.hide">
        Hide
      </b-dropdown-item>
      <b-dropdown-item href="javascript: void(0);" @click.prevent="plugin.hide = false" v-show="plugin.type !== '' && plugin.hide">
        Edit
      </b-dropdown-item>
      <b-dropdown-item href="javascript: void(0);" @click.prevent="$emit('copy-plugin', pluginType, index)" v-show="plugin.type !== ''">Copy
      </b-dropdown-item>
      <b-dropdown-item href="javascript: void(0);" @click.prevent="$emit('delete-plugin', pluginType, index)">Delete</b-dropdown-item>
    </b-dropdown>
  </b-row>
</template>
