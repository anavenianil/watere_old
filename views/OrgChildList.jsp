<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : OrgChildList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List"%>

<script>
	jsonChildArrayObject =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List)session.getAttribute("childList"))%>;
</script>

<!-- End : OrgChildList.jsp -->