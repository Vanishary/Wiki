<template>
    <a-layout id="components-layout-demo-top-side-2">
        <the-header></the-header>
<!--        <div id="v-content" v-bind:style="{minHeight: Height+'px'}"><router-view /></div>-->
        <router-view/>
        <the-footer></the-footer>
<!--        <the-footer id="the-footer-mode"></the-footer>-->
    </a-layout>
</template>

<script lang="ts">
    import {defineComponent} from 'vue';
    import TheHeader from '@/components/the-header.vue';
    import TheFooter from '@/components/the-footer.vue';

    export default defineComponent({
        name: 'app',
        // data() {
        //     return {
        //         Height: 0
        //     }
        // },
        components: {
            TheHeader,
            TheFooter,
        },
        // mounted(){
        //     //动态设置内容高度 让footer始终居底   header+footer的高度是100
        //     this.Height = document.documentElement.clientHeight - 100;
        //     //监听浏览器窗口变化　
        //     window.onresize = ()=> {this.Height = document.documentElement.clientHeight -100}
        // }
    });

</script>

<style>
    #components-layout-demo-top-side-2 .logo {
        float: left;
        width: 120px;
        height: 31px;
        margin: 16px 28px 16px 0;
        background: rgba(255, 255, 255, 0.2);
    }
    /*.footer {*/
    /*    flex: 0;*/
    /*}*/
</style>
