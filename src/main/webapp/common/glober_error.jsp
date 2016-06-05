<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<script type="text/javascript">
        var code = '${code}';
        if(code==8888){
        	alert("服务器发生错误，请联系管理员，谢谢!");
        }else if(code==8889){
        	alert('提交的数据包含敏感词，请修改后提交！');
        }else{
        	alert('未定义的错误，code＝['+code+']');
        }
		history.go(-1);
</script>
