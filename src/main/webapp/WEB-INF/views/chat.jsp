<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chat.css">
<div class="container-fluid" id="app">
    <div class="row clearfix">
        <p :key="key" hidden="hidden"></p>
        <div class="col-lg-12">
            <div class="card chat-app">
                <div id="plist" class="people-list" style="padding: 10px">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" style="width: 100%; height: 100%"><i
                                    class="fa-solid fa-magnifying-glass"></i></span>
                        </div>
                        <input type="text" v-model="search_users" class="form-control" placeholder="Search...">
                    </div>
                    <div class="col-md-12">
                        <ul class="list-unstyled chat-list mt-2 mb-0"
                            :style="{'overflow-y': 'scroll', 'height' : left_height, 'border': '2px solid #E9ECEF'}">
                            <template v-for="(value, key) in data">
                                <li @click="change_chatting_with(value.id)" v-bind:class="{'clearfix active' : (receiver_id == value.id), 'clearfix' : (receiver_id != value.id), 'hidden': search_users == '' ? false : !value.name.toLowerCase().includes(search_users.toLowerCase())}" class="clearfix">
                                    <img v-bind:src="host + '/' + value.avatar" alt="avatar" style="width: 50px; height: 50px; border-radius: 50%; object-fit: cover">
                                    <div class="about">
                                        <div class="name">{{value.admin ? 'admin' : 'user'}} : {{value.name}}</div>
                                        <div v-if="value.last_chat_sender == null">Chưa có tin nhắn</div>
                                        <div v-else>
                                            <div v-if="value.last_chat_sender == login_user.id && value.last_chat_is_img == false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">bạn : {{ value.last_chat_content }}</div>
                                            <div v-if="value.last_chat_sender != login_user.id && value.last_chat_is_img == false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{value.name}} : {{ value.last_chat_content }}</div>
                                            <div v-if="value.last_chat_sender == login_user.id && value.last_chat_is_img != false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">bạn : hình ảnh</div>
                                            <div v-if="value.last_chat_sender != login_user.id && value.last_chat_is_img != false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{value.name}} : hình ảnh</div>
                                        </div>
                                    </div>
                                </li>
                            </template>
                        </ul>
                        <div v-if="showing_preview" style="max-width: 100%; height: 30vh; margin-top: 5px;">
                            <img v-bind:src="preview_img_src" alt="" style="height: 100%; width: 100%;  object-fit: cover; border-radius: 10%;">
                        </div>
                    </div>

                </div>
                <div class="chat">
                    <div class="chat-header clearfix" ref="chat_header">
                        <div class="row">
                            <div class="col-lg-6">
                                    <img v-if="current_chat_index == -1" src="${pageContext.request.contextPath}/uploads/default-avatar.webp" alt="avatar">
                                    <img v-if="current_chat_index != -1" v-bind:src="host + '/' + user_list[current_chat_index].avatar" alt="avatar">
                                <div class="chat-about">
                                    <h6 class="m-b-0">{{current_chat_index != -1 ? data[current_chat_index].name : 'Chọn 1 người để chat'}}</h6>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="chat-history" ref="scroll_me" style="overflow-y: scroll; height:calc(70vh)">
                        <ul ref="container" class="m-b-0" v-if="current_chat_index != -1">
                            <template v-for="(value, key) in data[current_chat_index].mess">
                                <li class="clearfix" v-if="value.sender_id == login_user.id">
                                    <div class="message-data text-end">
                                        <span class="message-data-time">{{value.created_at}}</span>
                                        <img :src="host + '/' + login_user.avatar" alt="avatar">
                                    </div>
                                    <div class="message other-message float-right" v-if="!value.is_image">
                                        {{value.content}}
                                    </div>
                                    <div class="message other-message float-right" v-if="value.is_image">
                                        <img :src="host + '/' + value.content " alt="" style="max-width: 300px; max-height: 300px;">
                                    </div>
                                </li>

                                <li class="clearfix" v-else>
                                    <div class="message-data">
                                        <img :src="host + '/' + data[current_chat_index].avatar" alt="avatar">
                                        <span class="message-data-time">{{value.created_at}}</span>
                                    </div>
                                    <div class="message my-message" v-if="!value.is_image">
                                        {{value.content}}
                                    </div>
                                    <div class="message my-message" v-if="value.is_image">
                                        <img :src="host + '/' + value.content " alt="" style="max-width: 300px; max-height: 300px;">
                                    </div>
                                </li>
                            </template>
                        </ul>

                    </div>
                    <div class="chat-message clearfix">
                        <div class="input-group mb-0 col-md-12">
                                <form class="col-md-12" @submit="send_mess($event)">
                                <div class="input-group">
                                    <input v-model="mess" type="text" class="form-control" id="mess" placeholder="Recipient's username" aria-label="Recipient's username" aria-describedby="basic-addon2">

                                    <input @change="onChange($event)" ref="myRef" hidden type="file" id="image" accept="image/*">
                                    <div class="input-group-append">
                                        <label ref="label_to_click" for="image">
                                        </label>
                                        <button class="btn btn-outline-secondary" @click="chooseFile" type="button">
                                            <i class="fa-solid fa-image"></i>
                                        </button>
                                        <button class="btn btn-outline-secondary" type="submit"><i class="fa-solid fa-paper-plane"></i></button>
                                    </div>
                                </div>
                                </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../include/foot.jsp" %>
<script src="${pageContext.request.contextPath}/assets/js/http_cdnjs.cloudflare.com_ajax_libs_vue_2.7.10_vue.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/http_cdnjs.cloudflare.com_ajax_libs_axios_1.4.0_axios.js"></script>
<a href="${pageContext.request.contextPath}/user/chat/get-mess-with"></a>
<script>
    var app = new Vue({
        el: "#app",
        data: {
            socket : new WebSocket((window.location.protocol === 'https:' ? 'wss://' : 'ws://') + window.location.host + '${pageContext.request.contextPath}/my-websocket'),
            user_list: [],
            login_user: ${user_json},
            admin_list : [],
            normal_user_list : [],
            host : "${pageContext.request.contextPath}",
            current_chat_index: -1,
            receiver_id: 0,
            mess : "",
            file : null,
            showing_preview  : false,
            vh : 0,
            preview_img_src : '',
            data : [],
            key : 0,
            scroll_me : null,
            left_height : "80vh",
            search_users : ""
        },
        created() {
            const vh = Math.max(document.documentElement.clientHeight || 0, window.innerHeight || 0);
            this.vh = (vh * 0.01);
            this.getAllUsers();
            this.socket.onmessage = (event) => {
                const message = JSON.parse(event.data);
                let index = 0;
                if (message.sender_id == this.login_user.id){ // find with receiver_id
                    index = this.data.findIndex(item => item.id == message.receiver_id)
                } else {
                    index = this.data.findIndex(item => item.id == message.sender_id)
                }
                if (this.data[index].loaded === false){
                    const user_id_chat_with = this.data[index].id;
                    axios.get("${pageContext.request.contextPath}/user/chat/get-mess-with?user_id_chat_with=" + user_id_chat_with)
                        .then((res)=>{
                            this.data[index].mess = res.data
                            this.data[index].last_chat_content =message.content
                            this.data[index].last_chat_is_img =message.is_image
                            this.data[index].last_chat_sender =message.sender_id
                            this.data[index].last_chat_time =message.created_at
                            this.key++;
                            this.$nextTick(()=>{
                                setTimeout(() => {
                                    const container = this.$refs.container;
                                    const lastChild = container.lastElementChild;
                                    lastChild.scrollIntoView({ behavior: 'smooth' });
                                    this.shouldScroll = false;
                                }, 100);
                            })
                        })
                } else {
                    this.data[index].mess.push(message)
                    this.data[index].last_chat_content =message.content
                    this.data[index].last_chat_is_img =message.is_image
                    this.data[index].last_chat_sender =message.sender_id
                    this.data[index].last_chat_time =message.created_at
                    this.key++;
                    this.$nextTick(()=>{
                        setTimeout(() => {
                            const container = this.$refs.container;
                            const lastChild = container.lastElementChild;
                            lastChild.scrollIntoView({ behavior: 'smooth' });
                            this.shouldScroll = false;
                        }, 100);
                    })
                }

            }
        },
        methods: {
            onChange(e){
                this.file = e.target.files[0];
                this.showing_preview = true;
                this.preview_img_src = URL.createObjectURL(this.file);
                this.left_height = "50vh";
            },
            chooseFile(){
                this.$refs.myRef.click()
            },
            getAllUsers(){
                axios.get("${pageContext.request.contextPath}/user/chat/get-all-users")
                    .then((res)=>{
                        this.user_list = res.data
                        this.data = res.data;
                        for (let i = 0; i < this.data.length; i++) {
                            this.data[i].loaded=false
                        }
                        /*if (this.login_user.admin === true){
                            console.log("la admin")
                            this.data = res.data;
                            for (let i = 0; i < this.data.length; i++) {
                                this.data[i].loaded=false
                            }
                        } else {
                            console.log("0 la admin")
                            for (let i = 0; i < res.data.length; i++) {
                                console.log(res.data[i])
                                if (res.data[i].admin === true){
                                    console.log(res.data[i].name  +" la " + res.data[i].admin)
                                    this.data.push(res.data[i])
                                }
                            }
                        }*/
                        setTimeout(() => {
                            this.socket.send("subscribe:" + this.login_user.id.toString());
                        }, 1000);
                    })
            },
            change_chatting_with(id){
                this.receiver_id = id;
                this.current_chat_index = this.user_list.findIndex(item => item.id === this.receiver_id)
                if (this.data[this.current_chat_index].loaded === false){ // chua co mess, query mess
                    const user_id_chat_with = this.data[this.current_chat_index].id;
                    axios.get("${pageContext.request.contextPath}/user/chat/get-mess-with?user_id_chat_with=" + user_id_chat_with)
                        .then((res)=>{
                            this.data[this.current_chat_index].mess = res.data
                            this.key++;
                            this.$nextTick(()=>{
                                setTimeout(() => {
                                    const container = this.$refs.container;
                                    const lastChild = container.lastElementChild;
                                    lastChild.scrollIntoView({ behavior: 'smooth' });
                                    this.shouldScroll = false;
                                }, 100);
                            })
                        })
                }
                this.$nextTick(()=>{
                    setTimeout(() => {
                        const container = this.$refs.container;
                        const lastChild = container.lastElementChild;
                        lastChild.scrollIntoView({ behavior: 'smooth' });
                        this.shouldScroll = false;
                    }, 100);
                })
            },
            send_mess(e){
                e.preventDefault();
                if (this.receiver_id === 0){
                    toastr.warning("Vui lòng chọn 1 người để nhắn tin.")
                } else {
                    if (this.file == null && this.method === ""){
                        toastr.warning("Vui lòng nhập tin nhắn.")
                    } else {
                        if (this.mess !== ""){
                            var payload = {
                                "receiver_id" : this.receiver_id,
                                "content" : this.mess
                            }
                            axios.post("${pageContext.request.contextPath}/user/chat", payload)
                                .then((res)=>{
                                    if (res.data === true && res.status === 200){
                                        this.mess = ""
                                    }
                                })
                        }
                        if (this.file !== null){
                            const formData = new FormData();
                            formData.append('image', this.file);
                            axios.post("${pageContext.request.contextPath}/user/chat/upload-file?receiver_id=" + this.receiver_id, formData, {
                                headers: {
                                    'Content-Type': 'multipart/form-data'
                                }
                            })
                                .then((res)=>{
                                    if (res.data === true && res.status === 200){
                                        this.file = null;
                                        this.preview_img_src = "";
                                        this.showing_preview = false;
                                        this.left_height = "80vh";
                                    } else {
                                        toastr.error("đã có lỗi xảy ra.")
                                    }
                                })
                        }
                    }
                }
            },
        },
        mounted(){

        }
    })
</script>