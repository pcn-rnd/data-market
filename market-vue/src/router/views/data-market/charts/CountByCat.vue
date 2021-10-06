<script>
    import { PolarArea } from 'vue-chartjs'
    import axios from "axios"
    export default {
        extends: PolarArea,
        data() {
            return {
                collection : {
                    labels : [],
                    datasets : [
                        {
                            label : "Data One",
                            backgroundColor: [],
                            pointBackgroundColor: "white",
                            borderWidth: 1,
                            pointBorderColor: "#249EBF",
                            data : []
                        }
                    ]
                },
                options: {
                    maintainAspectRatio: true,
                    legend: {
                        position: 'top',
                    }
                }
            };
        },
        async mounted() {
            try{
                await axios('/ds/cat-cnt').then(
                    res => {
                        const values = res.data.body;
                        const lbs = [];
                        const dts = [];

                        for(let i in values) {
                            lbs.push(values[i][0]);
                            dts.push(values[i][1]);
                        }

                        this.collection.labels = lbs;
                        this.collection.datasets[0].data = dts;
                        this.collection.datasets[0].backgroundColor = this.colorGenerate(values.length)
                    }
                );
            }catch (e) {
                console.error(e);
            }

            this.renderChart(this.collection, this.options);
        },
        methods: {
            colorGenerate(len) {
                const colors = [];
                for(let i=0; i<len; i++){
                    colors.push('#'+Math.floor(Math.random()*16777215).toString(16));
                }
                return colors;
            }
        }

    }
</script>