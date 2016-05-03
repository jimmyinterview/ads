<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
    String path = request.getContextPath();
    String echarValue = (String)request.getAttribute("echarValue");
%>
<div class="row">
    <!-- 报表图形 -->
    <div id="rep_main" class="col-sm-12" style="height:400px;">

    </div>
</div>
<script src="<%=path %>/common/echarts/echarts.js"></script>
<script type="text/javascript">
    var value ='<%=echarValue %>'
    
    var path='<%=path %>';
    //路径配置
    require.config({
        paths: {
            echarts: path+"/common/echarts"
        }
    });
    //使用
    require(
            [
                'echarts',
                'echarts/theme/macarons',
                'echarts/chart/line',	// 使用折线图就加载line模块，按需加载
                'echarts/chart/bar'
            ],
            function (ec,theme) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('rep_main'),theme);

                option = {
                    title : {
                        text: '销售统计',
                        subtext: '数据统计'
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['销售量']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'category',
                            data : ['周一','周二','周三','周四','周五','周六','周日']
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'销售量',
                            type:'bar',
                            data:${echarValue},
                            markPoint : {
                                data : [
                                    {type : 'max', name: '最大值'},
                                    {type : 'min', name: '最小值'}
                                ]
                            },
                            markLine : {
                                data : [
                                    {type : 'average', name: '平均值'}
                                ]
                            }
                        }
                    ]
                };


                // 为echarts对象加载数据
                myChart.setOption(option);
            }
    );
 </script>
