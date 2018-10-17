/*@author zhanping.yang
 * 渲染富文本编辑框
 */
$('.summernote').each(
				function() {
					var $this = $(this);
					var placeholder = $this.attr("placeholder") || '';
					var url = $this.attr("action") || '';
					$this.summernote({
								lang : 'zh-CN',
								placeholder : placeholder,
								height : 300,
								dialogsFade : true,// Add fade effect on
													// dialogs
								dialogsInBody : true,// Dialogs can be placed
														// in body, not in
								// summernote.
								disableDragAndDrop : false,// default false You
															// can disable drag
								// and drop
								callbacks : {
									onImageUpload : function(files) {
										console.log("222222222");
										var $files = $(files);
										$files.each(function() {
													var file = this;
													var data = new FormData();
													data.append("uploadFile", file);
													$.ajax({
																data : data,
																type : "POST",
																url : url,
																cache : false,
																contentType : false,
																processData : false,
																success : function(data) {
																			// 文件不为空
																			$this.summernote(
																							'insertImage',
																							data,
																							function($image) {
																							});
																},
																error : {}
															});
												});
									}
								}
							});
					console.log($this.summernote);
				});

function iframeCallback(formId) {
	var $form = $("#"+formId);
	var flag = true;
//	var data = $form.data('bootstrapValidator');
//	if (data) {
//		if (!data.isValid()) {
//			flag = false ;
//		}
//	}
	// 富文本编辑器
	$("div.summernote", $form).each(
			function() {
				var $this = $(this);
				if (!$this.summernote('isEmpty')) {
					var editor = "<input type='hidden' name='"
							+ $this.attr("name") + "' value='"
							+ $this.summernote('code') + "' />";
					$form.append(editor);
				} else {
					alert("请填写项目详情");
					flag = false ;
				}
			});
	if(flag){
		$form.submit();
	}
}