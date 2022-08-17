<template>
    <a-layout>
        <a-layout-sider width="200" style="background: #fff">
            <a-menu
                    mode="inline"
                    :style="{ height: '100%', borderRight: 0 }"
            >
                <a-sub-menu key="sub1">
                    <template #title>
              <span>
                <user-outlined/>
                subnav 1
              </span>
                    </template>
                    <a-menu-item key="1">option1</a-menu-item>
                    <a-menu-item key="2">option2</a-menu-item>
                    <a-menu-item key="3">option3</a-menu-item>
                    <a-menu-item key="4">option4</a-menu-item>
                </a-sub-menu>
                <a-sub-menu key="sub2">
                    <template #title>
              <span>
                <laptop-outlined/>
                subnav 2
              </span>
                    </template>
                    <a-menu-item key="5">option5</a-menu-item>
                    <a-menu-item key="6">option6</a-menu-item>
                    <a-menu-item key="7">option7</a-menu-item>
                    <a-menu-item key="8">option8</a-menu-item>
                </a-sub-menu>
                <a-sub-menu key="sub3">
                    <template #title>
              <span>
                <notification-outlined/>
                subnav 3
              </span>
                    </template>
                    <a-menu-item key="9">option9</a-menu-item>
                    <a-menu-item key="10">option10</a-menu-item>
                    <a-menu-item key="11">option11</a-menu-item>
                    <a-menu-item key="12">option12</a-menu-item>
                </a-sub-menu>
            </a-menu>
        </a-layout-sider>
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
            <pre>{{ebooks}}</pre>
            <pre>{{any_name}}</pre>
        </a-layout-content>
    </a-layout>
</template>

<script lang="ts">
    import {defineComponent, onMounted, ref, reactive, toRef} from 'vue';
    import axios from 'axios';

    export default defineComponent({
        name: 'Home',
        // setup()中写参数定义、方法定义
        setup() {
            console.log("setup")
            // ref()定义响应式数据，即在js中动态的修改里面的值
            const ebooks = ref();
            const ebooks1 = reactive({books: []});

            onMounted(() => {
                // onMounted()中写入需要初始化的内容，如果直接写在setup()中可能界面还未初始化完成便为某个元素设置值会报错
                console.log("onMounted")
                axios.get("http://localhost:8880/ebook/list?name=Spring").then((response) => {
                    const data = response.data;
                    ebooks.value = data.content
                    ebooks1.books = data.content
                    console.log(response);
                });
            });

            return {
                ebooks,
                // toRef()将变量转变为响应式变量
                any_name: toRef(ebooks1, "books")
            }
        }
    });
</script>
