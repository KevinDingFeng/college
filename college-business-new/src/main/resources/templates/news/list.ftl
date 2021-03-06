<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>manage platform - 管理平台</title>
  <meta name="description" content="manage platform - 管理平台">
  <meta name="keywords" content="manage">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="icon" type="image/png" href="/assets/i/favicon.png">
  <link rel="apple-touch-icon-precomposed" href="/assets/i/app-icon72x72@2x.png">
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
  <link rel="stylesheet" href="/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="/assets/css/admin.css">
  <script src="/assets/js/jquery-2.1.4.min.js"></script>
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
    <strong>Business College</strong> <small>管理平台</small>
  </div>

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
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">新闻列表</strong> / <small>News List</small></div>
      </div>

      <hr>

      <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
              <button type="button" class="am-btn am-btn-default" onclick="javascript:window.location.href='/sys_new/form';"><span class="am-icon-plus"></span> 新增</button>
              	<#--
              <button type="button" class="am-btn am-btn-default"><span class="am-icon-save"></span> 保存</button>
              <button type="button" class="am-btn am-btn-default"><span class="am-icon-archive"></span> 审核</button>
              <button type="button" class="am-btn am-btn-default"><span class="am-icon-trash-o"></span> 删除</button>
              	-->
            </div>
          </div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-form-group">
            
          </div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">
          
        </div>
      </div>

      <div class="am-g">
        <div class="am-u-sm-12">
            <table class="am-table am-table-striped am-table-hover table-main">
              <thead>
              <tr>
                <#--<th class="table-check"><input type="checkbox" /></th>
                <th class="table-id">ID</th>-->
                <th class="table-title">标题</th>
                <th class="table-date am-hide-sm-only">发布日期</th>
                <th class="table-set">操作</th>
              </tr>
              </thead>
              <tbody>
              <#list page.content as em>
              <tr>
                <#--<td><input type="checkbox" /></td>
                <td>1</td>-->
                <td>${em.title!}</td>
                <td>${em.creation}</td>
                <td>
                	<#-- 查看、编辑、删除 -->
                  <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                      <a href="/news_view/${em.id}" target="_blank" class="am-btn am-btn-default am-btn-xs am-hide-sm-only"><span class="am-icon-archive"></span> 查看</a>
                      <a href="/sys_new/form?id=${em.id}" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span> 编辑</a>
                      <a href="javascript:void(0);" onclick="removeNews(${em.id})" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span> 删除</a>
                    </div>
                  </div>
                </td>
              </tr>
              <#else>
              <tr>
              	<td colspan="3" align="center">沒有符合的数据</td>
              </tr>
              </#list>
              </tbody>
            </table>
            <#--
            <#import "/ftl/component.ftl" as p>
			<@p.pageInfo page=pageInfo.page totalpages=pageInfo.totalPages 
			rownum=pageInfo.rowNum totalrownums=pageInfo.totalRowNums toURL="/exp_audit/"/>
			-->
            <form method="post" class="am-form" action="/sys_new" id="newsPageForm">
		        <input type="hidden" id="pageNum" name="pageNum" value="${pageNum!}" />
            <div class="am-cf">
            
              共 ${page.totalElements} 条记录
              <div class="am-fr">
                <ul class="am-pagination">
                  <#if pageNum == 0>
                  <li class="am-disabled"><a href="javascript:;">«</a></li>
                  <#else>
                  <li><a href="javascript:;" onclick="doPages(0)">«</a></li>
                  </#if>
                  
                  <#if pageNum == 0>
                  <li class="am-active"><a href="javascript:;">1</a></li>
                  	<#if page.totalPages gt 1>
                  	<li><a href="javascript:;" onclick="doPages(1)">2</a></li>
                  	</#if>
                  	<#if page.totalPages gt 2>
                  	<li>...</li>
                  	</#if>
                  <#else>
                    <#if pageNum gt 1>
                  	<li>...</li>
                  	</#if>
                  <li><a href="javascript:;" onclick="doPages(${pageNum - 1})">${pageNum}</a></li>
				  <li class="am-active"><a href="javascript:;">${pageNum + 1}</a></li>                  	
                  	<#if page.totalPages gt (pageNum + 1)>
                  	<li><a href="javascript:;" onclick="doPages(${pageNum + 1})">${pageNum + 2}</a></li>
                  	</#if>
                  	<#if page.totalPages gt (pageNum + 2)>
                  	<li>...</li>
                  	</#if>
                  </#if>
                  <#if page.totalPages == 0 || page.totalPages == (pageNum + 1)>
				  <li class="am-disabled"><a href="javascript:;">»</a></li>
                  <#else>
                  <li><a href="javascript:;" onclick="doPages(${pageNum + 1})">»</a></li>
                  </#if>
                </ul>
              </div>
            </div>
            </form>
            <script>
            function doPages(n){
            	$("#pageNum").val(n);
            	$("#newsPageForm").submit();
            }
            function removeNews(id){
            	
            	if(confirm("确定删除编号为" + id + "的新闻？")){
            		$.ajax({
					  type: 'POST',
					  url: "/sys_new/remove?id=" + id,
					  data: {},
					  success: function(res){
					  	console.log(res);
					  	if(res.code == 200){
							window.location.reload();
					  	}else{
					  		alert(res.data);
					  	}
					  }
					});
            	}else{
            		console.log("选择了取消");
            	}
            }
            </script>
            <hr />
        </div>
      </div>
    </div>

    <footer class="admin-content-footer">
      <hr>
      <p class="am-padding-left">© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
    </footer>
  </div>
  <!-- content end -->
</div>
<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="/assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
  <script src="/assets/js/jquery-2.1.4.min.js"></script>
<!--<![endif]-->
<script src="/assets/js/amazeui.min.js"></script>
<script src="/assets/js/app.js"></script>
</body>
</html>
