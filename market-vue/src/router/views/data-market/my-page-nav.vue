<script>
    export default {
        methods: {
            isList() {
                var prefix = '/my-page';
                if(window.location.pathname === prefix + '/list') return true;
                else return false;
            }
        }
    };
</script>
<template>
    <div class="col-lg-3">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title mb-4">My Page</h4>

            <div>
                <h5 class="font-size-14 mb-3 font-weight-bold">구매이력</h5>
                <ul class="list-unstyled product-list">
                    <li>
                        <a href="/my-page/list" :class="{ 'font-weight-bold':isList()}">
                            <i class="mdi mdi-chevron-right mr-1"></i> 구매 목록 조회
                        </a>
                    </li>
                    <li>
                        <a href="/my-page/list-bm" :class="{ 'font-weight-bold':!isList()}">
                            <i class="mdi mdi-chevron-right mr-1"></i> 내가 만든 데이터셋
                        </a>
                    </li>

                </ul>
            </div>

        </div>
    </div>
    </div>
</template>