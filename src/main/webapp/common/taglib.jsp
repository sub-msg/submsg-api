<%@page import="com.sr178.game.framework.config.ConfigLoader"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/aldmanagertags" prefix="aldtags"%>
<%@ taglib uri="/aldmanagerdttags" prefix="dt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	response.setCharacterEncoding("utf-8");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
	request.setAttribute("libUrl", ConfigLoader.getStringValue("lib_url", "/"));
	request.setAttribute("imgUrl", ConfigLoader.getStringValue("img_url", "/"));
	request.setAttribute("siteTitle", ConfigLoader.getStringValue("site_title", ""));
	request.setAttribute("siteAuthor", ConfigLoader.getStringValue("site_author", ""));
	request.setAttribute("siteKeywords", ConfigLoader.getStringValue("site_keywords", ""));
	request.setAttribute("siteDesc", ConfigLoader.getStringValue("site_desc", ""));
	request.setAttribute("siteCall", ConfigLoader.getStringValue("site_call", ""));
	request.setAttribute("siteIcp", ConfigLoader.getStringValue("site_icp", ""));
%>