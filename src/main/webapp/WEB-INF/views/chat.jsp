<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/head.jsp" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.1/css/toastr.css" rel="stylesheet"/>
<style type="text/css">
    body {
        background-color: #f4f7f6;
        margin-top: 20px;
    }

    .card {
        background: #fff;
        transition: .5s;
        border: 0;
        margin-bottom: 30px;
        border-radius: .55rem;
        position: relative;
        width: 100%;
        box-shadow: 0 1px 2px 0 rgb(0 0 0 / 10%);
    }

    .chat-app .people-list {
        width: 25%;
        position: absolute;
        left: 0;
        top: 0;
        padding: 20px;
        z-index: 7
    }

    .chat-app .chat {
        margin-left: 25%;
        border-left: 1px solid #eaeaea
    }

    .people-list {
        -moz-transition: .5s;
        -o-transition: .5s;
        -webkit-transition: .5s;
        transition: .5s
    }

    .people-list .chat-list li {
        padding: 10px 15px;
        list-style: none;
        border-radius: 3px
    }

    .people-list .chat-list li:hover {
        background: #efefef;
        cursor: pointer
    }

    .people-list .chat-list li.active {
        background: #efefef
    }

    .people-list .chat-list li .name {
        font-size: 15px
    }

    .people-list .chat-list img {
        width: 50px;
        border-radius: 50%
    }

    .people-list img {
        float: left;
        /*border-radius: 50%;
        width: 50px;
        height: 50px;
        object-fit: cover;*/
    }

    .people-list .about {
        float: left;
        padding-left: 8px
    }

    .people-list .status {
        color: #999;
        font-size: 13px
    }

    .chat .chat-header {
        padding: 15px 20px;
        border-bottom: 2px solid #f4f7f6
    }

    .chat .chat-header img {
        float: left;
        border-radius: 50%;
        width: 50px;
        height: 50px;
        object-fit: cover;
    }

    .chat .chat-header .chat-about {
        float: left;
        padding-left: 10px
    }

    .chat .chat-history {
        padding: 20px;
        border-bottom: 2px solid #fff
    }

    .chat .chat-history ul {
        padding: 0
    }

    .chat .chat-history ul li {
        list-style: none;
        margin-bottom: 30px
    }

    .chat .chat-history ul li:last-child {
        margin-bottom: 0px
    }

    .chat .chat-history .message-data {
        margin-bottom: 15px
    }

    .chat .chat-history .message-data img {
        border-radius: 50px;
        width: 50px;
        height: 50px;
        object-fit: cover;
    }

    .chat .chat-history .message-data-time {
        color: #434651;
        padding-left: 6px
    }

    .chat .chat-history .message {
        color: #444;
        padding: 18px 20px;
        line-height: 26px;
        font-size: 16px;
        border-radius: 7px;
        display: inline-block;
        position: relative
    }

    .chat .chat-history .message:after {
        bottom: 100%;
        left: 7%;
        border: solid transparent;
        content: " ";
        height: 0;
        width: 0;
        position: absolute;
        pointer-events: none;
        border-bottom-color: #fff;
        border-width: 10px;
        margin-left: -10px
    }

    .chat .chat-history .my-message {
        background: #efefef
    }

    .chat .chat-history .my-message:after {
        bottom: 100%;
        left: 30px;
        border: solid transparent;
        content: " ";
        height: 0;
        width: 0;
        position: absolute;
        pointer-events: none;
        border-bottom-color: #efefef;
        border-width: 10px;
        margin-left: -10px
    }

    .chat .chat-history .other-message {
        background: #e8f1f3;
        text-align: right
    }

    .chat .chat-history .other-message:after {
        border-bottom-color: #e8f1f3;
        left: 93%
    }

    .chat .chat-message {
        padding: 20px
    }

    .online,
    .offline,
    .me {
        margin-right: 2px;
        font-size: 8px;
        vertical-align: middle
    }

    .online {
        color: #86c541
    }

    .offline {
        color: #e47297
    }

    .me {
        color: #1d8ecd
    }

    .float-right {
        float: right
    }

    .clearfix:after {
        visibility: hidden;
        display: block;
        font-size: 0;
        content: " ";
        clear: both;
        height: 0
    }

    @media only screen and (max-width: 767px) {
        .chat-app .people-list {
            height: 465px;
            width: 100%;
            overflow-x: auto;
            background: #fff;
            left: -500px;
            display: none
        }

        .chat-app .people-list.open {
            left: 0
        }

        .chat-app .chat {
            margin: 0
        }

        .chat-app .chat .chat-header {
            border-radius: 0.55rem 0.55rem 0 0
        }

        .chat-app .chat-history {
            height: 300px;
            overflow-x: auto
        }
    }

    @media only screen and (min-width: 768px) and (max-width: 992px) {
        .chat-app .chat-list {
            height: 650px;
            overflow-x: auto
        }

        .chat-app .chat-history {
            height: 600px;
            overflow-x: auto
        }
    }

    @media only screen and (min-device-width: 768px) and (max-device-width: 1024px) and (orientation: landscape) and (-webkit-min-device-pixel-ratio: 1) {
        .chat-app .chat-list {
            height: 480px;
            overflow-x: auto
        }

        .chat-app .chat-history {
            height: calc(100vh - 350px);
            overflow-x: auto
        }
    }

    .custom-file-input {
        display: inline-block;
        padding: 10px 15px;
        color: #fff;
        cursor: pointer;
    }

    .custom-file-input i {
        margin-right: 5px;
    }

    #imageInput {
        display: none;
    }

    #imagePreview {
        /*max-width: 100%;*/
        /*max-height: 100%;*/
        /*object-fit: cover;*/
    }
</style>
<div class="container-fluid" id="app">
    <div class="row clearfix">
        <p :key="key"></p>
        <div class="col-lg-12">
            <div class="card chat-app">
                <div id="plist" class="people-list" style="padding: 10px">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" style="width: 100%; height: 100%"><i
                                    class="fa-solid fa-magnifying-glass"></i></span>
                        </div>
                        <input type="text" class="form-control" placeholder="Search...">
                    </div>
                    <div class="col-md-12">
                        <ul class="list-unstyled chat-list mt-2 mb-0"
                            style="overflow-y: scroll; height:calc(100vh - 450px)">
                            <template v-for="(value, key) in data" v-if="value.admin === true">
                                <li @click="change_chatting_with(value.id)" v-bind:class="{'clearfix active' : (receiver_id == value.id), 'clearfix' : (receiver_id != value.id)}" class="clearfix">
                                    <img v-bind:src="host + '/' + value.avatar" alt="avatar" style="width: 50px; height: 50px; border-radius: 50%; object-fit: cover">
                                    <div class="about">
                                        <div class="name">Admin : {{value.name}}</div>
                                        <div v-if="value.last_chat_sender == login_user.id && value.last_chat_is_img == false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">bạn : {{ value.last_chat_content }}</div>
                                        <div v-if="value.last_chat_sender != login_user.id && value.last_chat_is_img == false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{value.name}} : {{ value.last_chat_content }}</div>
                                        <div v-if="value.last_chat_sender == login_user.id && value.last_chat_is_img != false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">bạn : hình ảnh</div>
                                        <div v-if="value.last_chat_sender != login_user.id && value.last_chat_is_img != false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{value.name}} : hình ảnh</div>
                                        <div class="status"><i class="fa fa-circle offline"></i> left 7 mins ago</div>
                                    </div>
                                </li>
                            </template>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <template v-for="(value, key) in data" v-if="value.admin !== true">
                                <li @click="change_chatting_with(value.id)" v-bind:class="{'clearfix active' : (receiver_id == value.id), 'clearfix' : (receiver_id != value.id)}" class="clearfix">
                                    <img v-bind:src="host + '/' + value.avatar" alt="avatar" style="width: 50px; height: 50px; border-radius: 50%; object-fit: cover">
                                    <div class="about">
                                        <div class="name">User : {{value.name}}</div>
                                        <div v-if="value.last_chat_sender == login_user.id && value.last_chat_is_img == false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">bạn : {{ value.last_chat_content }}</div>
                                        <div v-if="value.last_chat_sender != login_user.id && value.last_chat_is_img == false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{value.name}} : {{ value.last_chat_content }}</div>
                                        <div v-if="value.last_chat_sender == login_user.id && value.last_chat_is_img != false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">bạn : hình ảnh</div>
                                        <div v-if="value.last_chat_sender != login_user.id && value.last_chat_is_img != false" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">{{value.name}} : hình ảnh</div>
                                        <div class="status"><i class="fa fa-circle offline"></i> left 7 mins ago</div>
                                    </div>
                                </li>
                            </template>

                        </ul>

                    </div>
                    <p v-show="showing_preview" style="margin: 10px;">Preview : </p>
                    <img v-bind:src="preview_img_src" alt="" style="max-width: 100%; max-height: 300px; object-fit: cover">
                </div>
                <div class="chat">
                    <div class="chat-header clearfix">
                        <div class="row">
                            <div class="col-lg-6">
                                    <img v-if="current_chat_index == -1" src="${pageContext.request.contextPath}/uploads/default-avatar.webp" alt="avatar">
                                    <img v-if="current_chat_index != -1" v-bind:src="host + '/' + user_list[current_chat_index].avatar" alt="avatar">
                                <div class="chat-about">
                                    <h6 class="m-b-0">{{current_chat_index != -1 ? data[current_chat_index].name : 'Chọn 1 người để chat'}}</h6>
                                    <small>Last seen: 2 hours ago</small>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="chat-history" ref="scroll_me" style="overflow-y: scroll; height:calc(100vh - 260px)">
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

                                <li class="clearfix" v-if="value.sender_id != login_user.id">
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
                        <ul ref="container" class="m-b-0" v-if="current_chat_index == -1">
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.1/js/toastr.js"></script>
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
        },
        async created() {
            console.log(this.$refs.container)
            const vh = Math.max(document.documentElement.clientHeight || 0, window.innerHeight || 0);
            this.vh = (vh * 0.01);
            await this.getAllUsers();
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
                        this.socket.send("subscribe:" + this.login_user.id.toString());
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
                                    } else {
                                        toastr.error("đã có lỗi xảy ra.")
                                    }
                                })
                        }
                    }
                }
            },
        },
    })
</script>