<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>manage platform - 管理平台</title>
  <meta name="description" content="manage platform - 管理平台">
  <meta name="keywords" content="form">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="icon" type="image/png" href="/assets/i/favicon.png">
  <link rel="apple-touch-icon-precomposed" href="/assets/i/app-icon72x72@2x.png">
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
  <link rel="stylesheet" href="/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="/assets/css/admin.css">
  
<script src="/assets/js/jquery-3.2.1.js"></script>
<script src="/assets/js/jquery.full.js"></script>
<script src="/assets/js/bootstrap-3.3.5.js"></script>
<link rel="stylesheet" href="/assets/css/bootstrap-3.3.5.css">
  <#-- 添加富文本编辑器必须的 js 和 css 文件 -->
	<link rel="stylesheet" href="/assets/summernote/summernote.css" >
	<script type="text/javascript" src="/assets/summernote/summernote.min.js"></script>
	<script type="text/javascript" src="/assets/summernote/lang/summernote-zh-CN.js"></script>
</head>
<style>
.modal-dialog{margin:52ps auto;}
</style>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
    <strong>Business College</strong> <small>管理平台</small>
  </div>

  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

  <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
      <#--
      <li><a href="javascript:;"><span class="am-icon-envelope-o"></span> 收件箱 <span class="am-badge am-badge-warning">5</span></a></li>
      <li class="am-hide-sm-only"><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
        <ul class="am-dropdown-content">
          <li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
          <li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
          <li><a href="#"><span class="am-icon-power-off"></span> 退出</a></li>
        </ul>
      -->
      <li class="am-dropdown" data-am-dropdown>
        <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
          <span class="am-icon-users"></span> ${(loginUser.name)!"管理员"} <#-- span class="am-icon-caret-down" --></span>
        </a>
      </li>
      <li class="am-dropdown" data-am-dropdown onclick="javascript:window.location.href='/logout'">
        <a class="am-dropdown-toggle" data-am-dropdown-toggle href="/logout">
          <span class="am-icon-power-off"></span> 退出<#-- span class="am-icon-caret-down" --></span>
        </a>
      </li>
    </ul>
  </div>
</header>

<div class="am-cf admin-main">
  <!-- sidebar start -->
  <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
      <ul class="am-list admin-sidebar-list">
        <li><a href="javascript:;"><span class="am-icon-home"></span> 首页</a></li>
        <li class="admin-parent">
          <a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span class="am-icon-file"></span> 新闻管理  <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
          <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav">
        	<li><a href="/sys_new"><span class="am-icon-table"></span> 新闻列表</a></li>
          </ul>
        </li>
        <li class="admin-parent">
          <a class="am-cf" data-am-collapse="{target: '#special-nav'}"><span class="am-icon-file"></span> 专题管理  <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
          <ul class="am-list am-collapse admin-sidebar-sub am-in" id="special-nav">
            <li><a href="/sys_special"><span class="am-icon-table"></span> 专题列表</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
  <!-- sidebar end -->

<!-- content start -->
<div class="admin-content">
  <div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
      <div class="am-fl am-cf">
        <strong class="am-text-primary am-text-lg">预览</strong> / <small>View News</small>
      </div>
    </div>

    <hr>
    <div class="am-tabs am-margin" data-am-tabs>
      <ul class="am-tabs-nav am-nav am-nav-tabs">
        <li class="am-active"><a href="#tab1">新闻信息</a></li>
      </ul>
	<article class="blog-main">
      <h3 class="am-article-title blog-title">
        <a href="#">${entity.title!}</a>
      </h3>
      <h4 class="am-article-meta blog-meta">${entity.creation}</h4>
      <div class="am-g blog-content">
        <div class="am-u-lg-7">
            <img src="/f/${entity.headImg!}" />
        </div>
      </div>
      <div class="am-g blog-content">
        <div class="am-u-lg-7">
          ${(entity.abstractText!'')}
        </div>
      </div>
      <hr />
      <div class="am-g blog-content">
        <div class="am-u-lg-7">
          ${(entity.content!'')}
        </div>
      </div>
    </article>
    </div>
    </div>
    
      <footer class="admin-content-footer">
	    <hr>
	    <p class="am-padding-left">© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
	  </footer>  
    
	<script type="text/javascript" src="/assets/summernotejs/summernote.js"></script>
  </div>
<!-- content end -->
</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

</body>
</html>
