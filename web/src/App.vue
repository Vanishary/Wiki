<template>
    <a-layout id="components-layout-demo-top-side-2">
        <the-header></the-header>
        <router-view/>
        <a-layout-footer style="text-align: center">
            @Wiki EPRA
        </a-layout-footer>
    </a-layout>
</template>

<script lang="ts">
    import {defineComponent} from 'vue';
    import TheHeader from '@/components/the-header.vue';
    import TheFooter from '@/components/the-footer.vue';

    export default defineComponent({
        name: 'app',
        components: {
            TheHeader,
            TheFooter,
        }
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

    .ant-layout-sider {
        float: left;
    }
</style>
