<template>
    <a-layout-footer style="text-align: center" >
        @Wiki EPRA
    </a-layout-footer>
</template>

<script lang="ts">
    import { defineComponent } from 'vue';

    export default defineComponent({
        name: 'the-footer'
    });
</script>

<style scoped>
    .m-content-display {
        display: flex;
        flex-direction: column;
        min-height: 100%;
    }
    .m-router-view {
        flex: 1;
    }
</style>