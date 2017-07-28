var winHeight = document.body.offsetHeight,
    winWidth = document.body.offsetWidth,
    headHeight = $(".head-wrapper").outerHeight(),
    footerHeight = $("#footer").outerHeight(),
    swiperPageHeight = winHeight - headHeight - footerHeight ,
    mesShowMT = (winHeight - 167)/2,
    setMesHeight = (winHeight-206)/2,
    detailSwiper,
    t;
var page,
  boxData1="",
  boxData2="",
  type_detail=0,
  type_budget = 1,
  type_income=2,
  type_pay=3,
  type_inConsume=4,
  type_payConsume=5,
  chart,
  type_adddetail = 6,
  type_bigpaytype = 7,
  type_index = 8,
  type_manage = 9;
  
 var isTest=false;
function showDataList(parentDiv,data,type){
  $.each(data, function(index,dataItem){
    if(type==type_pay){
      var $oneDiv = showOneData(dataItem,type);
      $("#"+parentDiv).append($oneDiv);
      var moreData = postData("showPayType",index+1);
    //  var moreData = [{"paytypename":"早餐","bigpaytypeid":"1","paytypeid":"1"},{"paytypename":"午餐","bigpaytypeid":"1","paytypeid":"2"},{"paytypename":"晚餐","bigpaytypeid":"1","paytypeid":"3"},{"paytypename":"饮料水果","bigpaytypeid":"1","paytypeid":"4"},{"paytypename":"零食烟酒","bigpaytypeid":"1","paytypeid":"5"},{"paytypename":"买卖原料","bigpaytypeid":"1","paytypeid":"6"},{"paytypename":"夜宵","bigpaytypeid":"1","paytypeid":"7"},{"paytypename":"油盐酱醋","bigpaytypeid":"1","paytypeid":"8"},{"paytypename":"餐饮其他","bigpaytypeid":"1","paytypeid":"9"}];
      $more=$("<div/>").addClass("display-none").clone();
      for (var key in moreData){
        var $detailToggle = $("<div/>").addClass("more-info").text(moreData[key].paytypename).clone()
            .attr({"onClick":"changeVal("+parentDiv+","+type+",this)","data-value":moreData[key].paytypeid});
        var $splitHorizon = $("<div/>").addClass("split-horizon");
        $more.append($detailToggle,$splitHorizon);
      }
      $("#"+parentDiv).append($more);
    }else{
      if(type==type_detail||type==type_budget){
        var $oneDiv = showOneData(dataItem,type);
        $("#"+parentDiv).append($oneDiv);
        $more=$("<div/>").addClass("display-none").clone();
        var dataObj = {};
        if(type==type_detail){
          dataObj["收支成员"] = dataItem.consumename;
          dataObj["地点"] = dataItem.adress;
          dataObj["时间"] = dataItem.timepoint;
          dataObj["备注"] = dataItem.detailmark;
          $oneDiv.attr("onclick",'window.location.href=(encodeURI("modify-detail.html?detailid='+dataItem.detailid+
            '&consumename='+dataItem.consumename+'&adress='+dataItem.adress+'&detailmark='+dataItem.detailmark+
            '&timepoint='+dataItem.timepoint+'&detailname='+dataItem.detailname+'&detailmoney='+checkMoney(dataItem.detailmoney)+
            '&detailszclassify='+dataItem.detailszclassify+'"))');
        }
        if(type==type_budget){
          dataObj["收支成员"] = dataItem.consumename;
          dataObj["预算年月"] = dataItem.budgettimepart.substring(0,7);
          dataObj["备注"] = dataItem.budgetmark;
          $oneDiv.attr("onclick",'window.location.href=(encodeURI("modify-budget.html?budgetid='+dataItem.budgetid+
            '&consumename='+dataItem.consumename+'&budgetmark='+dataItem.budgetmark+'&budgettimepart='+dataItem.budgettimepart.substring(0,7)+
            '&budgetname='+dataItem.budgetname+'&budgetmoney='+checkMoney(dataItem.budgetmoney)+'&budgetszclassify='+dataItem.budgetszclassify+'"))');
        }
        for(var key in dataObj){
          var $detailToggle = $("<div/>").addClass("more-info").text(key+": "+dataObj[key]).clone();
          var $splitHorizon = $("<div/>").addClass("split-horizon");
          $more.append($detailToggle,$splitHorizon);
        }
        $("#"+parentDiv).append($more);
      }else{
        var $oneDiv = showOneData(dataItem,type,parentDiv);
        if(type!=type_income||type!=type_bigpaytype){
          $oneDiv.attr("onClick","changeVal("+parentDiv+","+type+",this)");
        }
        $("#"+parentDiv).append($oneDiv); 
      }
    }
  });
}
function checkMoney(money){
  if(!(money.indexOf(".") > 0)){
    return money+".00";
  }else{
    return money;
  }
}
function showOneData(dataItem,type,parentDiv){
  var $oneDetail = $("<div/>").addClass("one-detail");
    var $detailLeft = $("<div/>").addClass("float-left pl-md pt-sm");
      var $spanLeft1 = $("<span/>").addClass("detail-bar");
      var $spanLeft2 = $("<span/>");
      var name="";
      switch(type){
        case type_detail  : (dataItem.detailszclassify==0?$spanLeft1.addClass("bg-theme"):$spanLeft1.addClass("bg-green"));
                            name=dataItem.detailname;break;
        case type_budget  : (dataItem.budgetszclassify==0?$spanLeft1.addClass("bg-theme"):$spanLeft1.addClass("bg-green"));
                            name=dataItem.budgetname;break;
        case type_income  : $spanLeft1.addClass("bg-green"); name=dataItem.incometypename;
                            $spanLeft2.attr({"data-value":dataItem.incometypeid,"onClick":"changeVal("+parentDiv+","+type+",this)"}).addClass("pr-120");break;
        case type_pay     : $spanLeft1.addClass("bg-theme"); name=dataItem.bigpaytypename;break;
        case type_inConsume: $spanLeft1.addClass("bg-green"); name=dataItem.consumename;break;
        case type_payConsume:$spanLeft1.addClass("bg-theme"); name=dataItem.consumename;break; 
        case type_bigpaytype:$spanLeft1.addClass("bg-theme"); name=dataItem.bigpaytypename;
                             $spanLeft2.attr({"data-value":dataItem.bigpaytypeid,"onClick":"changeVal("+parentDiv+","+type+",this)"}).addClass("pr-120");break; 
      }
      $spanLeft2.text(name);
      $detailLeft.append($spanLeft1,$spanLeft2);
    var $detailRight = $("<div/>").addClass("float-right pt-sm");
    if(type==type_detail||type==type_pay||type==type_budget){
      var $spanRight3 = $("<span/>").addClass("glyphicon glyphicon-chevron-up arrow").attr({"onClick":"showMore(this,event,"+type+")","active":"true"});//下拉按钮
      if(type==type_detail||type==type_budget){
        var szClassBit;
        var $spanRight2 = $("<span/>");
        if(type==type_detail){
          szClassBit = dataItem.detailszclassify;
          $spanRight2.text(dataItem.detailmoney);//添加money
        }else{
          szClassBit = dataItem.budgetszclassify;
          $spanRight2.text(dataItem.budgetmoney);//添加money
        }
        if(szClassBit==0){
          var $spanRight1 = $("<span/>").addClass("theme-color").text("-");
          $spanRight3.addClass("theme-color");
        }else{
          var $spanRight1 = $("<span/>").addClass("text-green").text("+");
          $spanRight3.addClass("text-green");
        }
        /*$spanRight2 判断有无 颜色随机*/
        $detailRight.append($spanRight1,$spanRight2,$spanRight3);
      }
      if(type==type_pay){
        $spanRight3.addClass("theme-color");
      }
      $detailRight.append($spanRight3);
    }
  $oneDetail.append($detailLeft,$detailRight);
  return $oneDetail;
}

function changeVal(parentId,type,obj){
  var aimId=$(parentId).parent().parent().parent().parent().parent().attr("data-aim");
  $("#"+aimId).val($(obj).text());
  if(type==type_income||type==type_pay||type==type_bigpaytype){
    $("#"+aimId).attr("data-value",$(obj).attr("data-value"));
  }
  if(type==type_detail&&type==type_pay){
    $("span[active='false']").parent().parent().next().slideUp();
    $("span[active='false']").attr("active","true").addClass("transform-rotate0").removeClass("transform-rotate180");
  }
  closePageFromPar(parentId);
}

function showMore(obj,event,type){
  var $presentDiv = $(obj);
  var $moreDiv=$presentDiv.parent().parent().next();
  var active = $presentDiv.attr("active");
  if(active=="true"){
    $(".arrow").addClass("transform-rotate0").removeClass("transform-rotate180");
    $("span[active='false']").parent().parent().next().slideUp("slow",function(){
      if(type!=type_budget){
        detailSwiper.reInit();
      }
    }).addClass("display-none");
    $presentDiv.addClass("transform-rotate180").removeClass("transform-rotate0");
    $moreDiv.slideDown("slow",function(){
      if(type!=type_budget){
        detailSwiper.reInit();
      }
    }).removeClass("display-none");
    $presentDiv.attr("active","false");
  }else{
    $presentDiv.addClass("transform-rotate0").removeClass("transform-rotate180");
    $moreDiv.slideUp("slow",function(){
      if(type!=type_budget){
        detailSwiper.reInit();
      }
    }).addClass("display-none");
    $presentDiv.attr("active","true");
  }
  event.stopPropagation();
}

function selectType(obj){
  $(obj).next().slideToggle();
}
// 下拉选择框
function selectTypeVal(obj){
  $(obj).parent().prev().children().eq(0).text($(obj).text());
  $(obj).parent().slideUp();
}

function changePage(i){
  $($("#headInfo").children().get(i)).addClass("active");
  $($("#headInfo").children().get(1-i)).removeClass("active");
  page.swipeTo(i);
}
function initSwiper(swiperContainer){
  detailSwiper = new Swiper("#"+swiperContainer,{
    mode:'vertical',
    mousewheelControl: true,
    calculateHeight:true,
    scrollContainer: true,     //设置整个Swiper作为可滚动区域，制作滚动条
  });
}

function initPageSwiper(swiperPage,num,type){
  page = new Swiper("#"+swiperPage, {
    mode:'horizontal',
    initialSlide :num,
    onSlideChangeStart: function(){
      if(type==type_adddetail){
        $(".swiper-nav .active").removeClass("active");
        $(".swiper-nav .swiper-slide").eq(page.activeIndex).addClass("active");
      }else{
        $($("#headInfo").children().get(page.activeIndex)).addClass("active");
        $($("#headInfo").children().get(1-page.activeIndex)).removeClass("active");
      }
    }
  });
}

function bindDate(id) {
  $('#'+id).mobiscroll().date({
    preset : 'date',
    theme: 'android-ics light', 
    mode: 'scroller',  // 以wheel滑动方式选择
    display: 'modal',  //显示在中间
    lang: 'zh' ,
  // cancelText:null,    // 取消按钮
    dateOrder: 'yymmdd',
    dateFormat: 'yy-mm-dd',  //返回结果格式
    maxDate: new Date(new Date().setFullYear(2100,12,31)),//设置最大日期
    // minDate: new Date() //设置最小日期
  });
}
// 预算年月
function bindYearMonth(id) {
  var nowDate = new Date();
  var year = nowDate.getFullYear();
  var month = nowDate.getMonth();
  $('#'+id).mobiscroll().date({
    preset : 'date',
    theme: 'android-ics light', 
    mode: 'scroller',  // 以wheel滑动方式选择
    display: 'modal',  //显示在中间
    lang: 'zh' ,
  // cancelText:null,    // 取消按钮
    dateOrder: 'yymm',
    dateFormat: 'yy-mm',  //返回结果格式
    maxDate: new Date(new Date().setFullYear(2100,12)),//设置最大日期
    minDate: new Date(new Date().setFullYear(year,month)) //设置最小日期
  });
}
function formatDate(flag){
  var nowDate = new Date();
  var month = nowDate.getMonth();
  var day = nowDate.getDate();
  var date;
  if(month<9){
    date = nowDate.getFullYear().toString()+"-0"+(month+1).toString();  
  }else{
    date = nowDate.getFullYear().toString()+"-"+(month+1).toString();//+"-"+nowDate.getDate().toString();
  }
  if(flag!="noDay"){
    if(flag=="startTime"){
      date = date + "-01";
    }else{
      if(day<10){
        date = date +"-0"+nowDate.getDate().toString();
      }else{
        date = date + "-"+nowDate.getDate().toString();
      }
    }
  }
  return date;
}
function nowMonthAddOne(){
  var nowDate = new Date();
  var month = nowDate.getMonth()+2;
  var date;
  if(month<=9){
    date = nowDate.getFullYear().toString()+"-0"+(month).toString();  
  }else{
    date = nowDate.getFullYear().toString()+"-"+(month).toString();
  }
  return date;
}
function setChartData(dataWrapper){
  chart.series[0].setData(dataWrapper[0]);
  chart.xAxis[0].setCategories(dataWrapper[1]);
}
function setChartTitle(titleText,xAxisTitle){
  var title = {
    text:titleText,
    style:{
      color:'rgb(240,040,066)',
      fontSize:'20px',
      fontWeight:'bold',
    }
  };
  chart.setTitle(title);
  var xTitle = {
    text:xAxisTitle,
  };
  chart.xAxis[0].setTitle(xTitle);
}
      
function getSeriesData(data){
  var dataWrapper = [];
  var dataPart = [];
  var dataAll = [];
  for(var key in data){
    var dataInner = [];
    dataInner[0] = data[key].name;
    dataInner[1] = parseFloat(data[key].money);
    dataWrapper[key] = dataInner;
    dataPart[key] = data[key].name;
  }
  dataAll[0] = dataWrapper;
  dataAll[1] = dataPart;
  return dataAll;
}

function createChart(id,typeName,xAxisTitle,titleInfo,isDouble){
  var byPoint;
  var enable;
  if(!isDouble){
    byPoint=true;
    enable=false;
  }else{
    byPoint=false;
    enable=true;
  }
  chart = new Highcharts.Chart({
    chart:{
      renderTo:id,
      type:typeName,
      animation:true,
      // 3d效果
      options3d:{
        enabled:true,
        alpha:45,
        beta:0,
      },
      borderWidth:1,
      borderColor:"#888",
      borderRadius:5,
      shadow:true,
    //  zoomType:'xy',
      
    //  backgroundColor:"#fff",
      // 绘图区的设置
      plotBackgroundColor:null,
      plotBorderWidth:0,
      plotBorderColor:null,
      plotShadow:true, 
    },
  /*  lang:{
      printChart:"打印图表",
      downloadJPEG: "下载JPEG 图片",
      downloadPDF: "下载PDF文档",
      downloadPNG: "下载PNG 图片",
      downloadSVG: "下载SVG 矢量图",
      exportButtonTitle: "导出图片",
    },*/
    colors: ['#058DC7', '#50B432', '#ED561B','#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'] ,
    title:{
      text:"成员-支出",
      x:0,
      style:{
        color:'rgb(240,040,066)',
        fontSize:'20px',
        fontWeight:'bold',
      }
    },
    plotOptions:{
      series:{
        animation:true,
        borderWidth:1,
        allowPointSelect:true,
      },
      column:{
        colorByPoint:byPoint,
        tooltip:{
          headerFormat: '<small style="color: rgb(240,040,066)">'+titleInfo+'</small><br/>',      //数据提示框头部格式化字符
          pointFormat: '<span style="color: {point.color}">{point.name}:{point.y}￥</span>',
        },
        dataLabels:{
          enabled:true,
          formatter:function(){
            return this.y+"￥";
          },
        }
      },
      pie:{
        depth:25,     // 设置深度属性
        //innerSize:60,
        tooltip:{
          headerFormat: '<small style="color: rgb(240,040,066)">'+titleInfo+'</small><br/>',      //数据提示框头部格式化字符
          pointFormat: '<span style="color: {point.color}">{point.name}: {point.percentage:.1f}% </span>',
        },
      }
    },
    yAxis:{
      title:{
        text:'金额（￥）',
        style:{
          fontSize:13,
        },
      },
    },
    xAxis:{
      title:{
        text:xAxisTitle,
        style:{
          fontWeight:'bold',
          fontSize:13
        },
      },
      labels:{           // 坐标轴标签
        enabled:true,     // 是否启用label，默认为true
        formatter:function(){ //标签格式化函数
          return this.value; //除value变量，还有isFirst，isLast，axis,chart
        },
        rotation:45,
        step:1,       // labels显示间隔，默认为1
        staggerLines:1, //水平轴labels显示行数，只对水平轴有效
      },
      categories:[],   
      
     // tickInterval,   //刻度间隔
     // tickInterval:10,
      tickmarkPlacement:'on' ,  //刻度对齐方式
          
      gridLineWidth:1,    //坐标轴网格线
      gridLineColor:'#C0C0C0',
      gridLineDashStyle:'Dot',
     //  staggerLines:2,
    },
    legend:{
      enabled:enable,
     // 图例容器样式
    /*  backgroundColor:"#fff",
      borderColor:"#666",
      shadow:true, 
      floating:false,
      verticalAlign:"center",
      layout:"horizontal",
     // 图例项样式
       itemDistance:20,
      itemStyle:{
        cursor:"pointer",
        color:"#3E576F",
      },
      itemHoverStyle:{
        color:'blue',
      }
      layout:'horizontal',
    //  backgroundColor:'#fff',
      align:'center',
      verticalAlign:'bottom',
    //  x:90,
    //  y:45,
      floating:false,*/
    },
    credits:{
      enabled:false,
    },
    exporting:{
      enabled:false,
    }
/*  exporting:{
      enabled:true,
      filename:"chart",
      type:"image/png",
      width:800,
      buttons:{          //配置按钮选项
        printButton:{   //配置打印按钮
          width:126,
          height:26,
          symbolSize:20,
          symbolX:25,
          symbolY:15,
          borderWidth:2,
          borderRadius:5,
          x:-100,
          y:26,
          hoverBorderColor:'red',
          backgroundColor:'red',
        },
        exportButton:{   // 配置导出按钮
          backgroundColor:'red',
          width:126,
          height:26,
        
          symbolSize:20,
          symbolX:25,
          symbolY:15,
          
          borderWidth:2,
          borderRadius:5,
          x:-100,
          y:26,
          hoverBorderColor:'red',
        }
      },
    },*/
  });
  if(!isDouble){
    chart.addSeries([{}]);
  }
}



function complete(){
  closeCenterPage();
}
  
function closeCenterPage(){
  $("#userCenter").removeClass("show");
  $(".cover-div").removeClass("show");
  setTimeout(function(){
    $("#userCenter").addClass("display-none");
    $(".cover-div").addClass("display-none");
  },100);
}

function initNavSwiper(swiperNav){
  var nav = new Swiper("."+swiperNav,{
    slidesPerView: 'auto',     //slider容器能够同时显示的slides数量
    freeMode:true,             //若为true则是自由模式，不会自动贴合滑动位置
    noSwiping : true,          //设为true时，可以在slide上增加类名'swiper-no-swiping'，使该slide无法滑动。
    freeModeFluid:true,       //若为true，释放滑块之后仍会滑动一点。
    calculateHeight : true,   //当值为true时，Swiper根据slides内容计算容器高度。
    visibilityFullFit: true,  //如果启用，仅有“可视”的slides会最后适应容器的大小
    onSlideClick: function(nav){
      page.swipeTo( nav.clickedSlideIndex )
    }
  });
}
function pullHidden(id){
  var $obj = $("#"+id);
  if(!$obj.hasClass("show-wrapper")){
    $obj.removeClass("display-none");
    setTimeout(function(){
      $obj.addClass("show-wrapper").removeClass("hidden-again");
    },50);
  }else{
    $obj.addClass("hidden-again").removeClass("show-wrapper");
    setTimeout(function(){
      $obj.addClass("display-none");
    },600);
  }
}
function setData(title){
  stopScroll();
  var $noteWrapper = $("<div/>").addClass("note-wrapper");
    var $noteDiv = $("<div/>").addClass("div-note frame");
      var $titleDiv = $("<div/>").addClass("theme-color").text(title);
        var $spanIcon = $("<span/>").addClass("glyphicon glyphicon-remove mes-close-icon mr-none").attr("onclick","mesClose()");;
      $titleDiv.append($spanIcon);
      var $inputDiv1 = $("<div/>").addClass("land-wrapper");
        var $inputText1 = $("<input/>").attr({"type":"text","placeholder":"请设置问题"}).addClass("land-text set-password");
      $inputDiv1.append($inputText1);  
      var $inputDiv2 = $("<div/>").addClass("land-wrapper");
        var $inputText2 = $("<input/>").attr({"type":"text","placeholder":"请设置答案"}).addClass("land-text set-password");
      $inputDiv2.append($inputText2);
      var $setBtn = $("<div/>").addClass("setpw-button edit-button").attr({"onClick":"setBoxData(this)","id":"aaa"}).text("完成");
    $noteDiv.append($titleDiv,$inputDiv1,$inputDiv2,$setBtn);
  $noteWrapper.append($noteDiv);
  $noteWrapper.css("top",setMesHeight+"px");
  $("body").append($noteWrapper); 
}
function stopScroll(){
  $(document.body).css({
    "overflow-y":"hidden"
  })
}
function startScroll(){
  $(document.body).css({
    "overflow-y":"auto"
  });
}

function setBoxData(obj){
  var question = $(obj).parent().children().eq(1).children().eq(0).val();
  var answer = $(obj).parent().children().eq(2).children().eq(0).val();
  if(question!=""){
    boxData1 = question;
    boxData2 = answer;
    mesClose();
  }
}
function clearBoxData(){
  boxData1="";
  boxData2="";
}

function mesShow(content,statu,time){
  if($(".note-wrapper").length!=0){
    $(".note-wrapper").remove();
  }
  var icon="";
  switch (statu){
    case "success":icon="glyphicon-ok-sign";break;
    case "warn":icon="glyphicon-exclamation-sign";break;
    case "error":icon="glyphicon-remove-sign";break;
    case "info":icon="glyphicon-info-sign";break;
    default:;break;
  }
  clearTimeout(t);
  var $wrapper=$("<div/>").addClass("note-wrapper");
  var $note=$("<div/>").addClass("div-note "+statu).attr("onclick","mesClose()");
      var $inDiv=$("<div/>");
        var $icon=$("<span/>").addClass("glyphicon "+ icon +" mes-icon");
        var $closeIcon=$("<span/>").addClass("glyphicon glyphicon-remove mes-close-icon");
      $inDiv.append($icon,$closeIcon);
      var $content=$("<span/>").text(content);
  $note.append($inDiv,$content); 
  $wrapper.append($note);
  $wrapper.css("top",mesShowMT+"px");
  $("body").append($wrapper);
  if(!time){
    time=1000;
  }
  t=setTimeout(function(){
    $(".note-wrapper").fadeOut(time);
    t=setTimeout(mesClose,time);
  },time*2);
}
function mesClose(){
  $(".note-wrapper").remove();
  startScroll();
}
function wrapperRaise(id){
  $("#"+id).removeClass("display-none");
  setTimeout(function(){
    $(".content-wrapper").addClass("raise");
    $(".sztype-content").addClass("raise");
  },100)
}
function closePage(obj){
  $obj = $(obj);
  $(".content-wrapper").removeClass("raise");
  $(".sztype-content").removeClass("raise");
  setTimeout(function(){
    $obj.parent().parent().addClass("display-none");
  },300)
}
function closePageFromPar(parentId){
  var $obj=$(parentId).parent().parent().parent().parent().parent();
  $(".content-wrapper").removeClass("raise");
  $(".sztype-content").removeClass("raise");
  setTimeout(function(){
    $obj.addClass("display-none");
  },300)
}

/*        计算器表格          */
function delAllNum(id){
  $("#"+id).val("0.00");
  isLNum = false;
  isFirst = true;
  isScend = true;  
}
function delNum(id){
  var oriNum = $("#"+id).val();
  var foreNum = oriNum.split(".")[0];
  var hindNum = oriNum.split(".")[1];
  if(isLNum){
    if(!isScend){
      hindNum = hindNum.charAt(0)+"0";//hindNum.replace(hindNum.charAt(1),"0");
      isScend = true;  
    }else{
      if(!isFirst){
        hindNum = "00";//hindNum.replace(hindNum.charAt(0),"0");
        isFirst = true;
      }
    }
    if(isScend&&isFirst){
      isLNum = false;
    }
  }else{
    if(numLength!=0){
      if(numLength>1){
        foreNum = foreNum.substring(0,foreNum.length-1);//replace(foreNum.charAt(numLength-1),"");
      }else{
        foreNum = "0";//replace(foreNum.charAt(numLength-1),"0");
      }
      numLength = numLength - 1;
    }
  }
  $("#"+id).val(foreNum+"."+hindNum);
}
var isLNum = false;
var isFirst = true;
var isScend = true;
var numLength = 0;
function changeNum(num,id){
  var oriNum = $("#"+id).val();
  var foreNum = oriNum.split(".")[0];
  var hindNum = oriNum.split(".")[1];
    if(!isLNum&&num!="."&&numLength<12){
      if(foreNum!="0"){
        foreNum = foreNum+num;
      }else{
        foreNum = num;
      }
      numLength = foreNum.length;
    }else{
      if(num=="."){
        isLNum = true;
      }else{
        if(isLNum&&isFirst){
          hindNum = (num+hindNum).substring(0,2);
          isFirst = false;
        }else{
          if(isLNum&&isScend){
            hindNum = (hindNum.charAt(0)+num).substring(0,2);
            isScend = false;
          }
        }
      }
    }
    $("#"+id).val(foreNum+"."+hindNum);
}
function showNumPage(id){
  $("#"+id).animate({bottom:"0px"});
}
function closeNumPage(id){
  $("#"+id).animate({bottom:"-200px"});
}



//[notnull,date,datetime,number,length:x,email,phone,idnum,express:xxx]
//data-name='name'  data-valid='notnull'
/*  获取表单的键值  */
function getFormVal(formId){
  var dataObj = getElemsObj(formId,"data-name");
  return(dataObj);
}
function getElemsObj(formId,name){
  $inputs = $("#"+formId).find('['+name+']');
  var dataObj = {};  //json对象  键值对
  //data["name"]="shi";
  $.each($inputs,function(i,input){
    var property = $(input).attr(name);
    var value = $(input).val();
    dataObj[property] = value;
  });
  return dataObj;
}
function setFormVal(formId,obj){
  $inputs = $("#"+formId).find('[data-name]');
  $.each($inputs,function(i,input){
    $(input).val(obj[$(input).attr('data-name')])
  });
}
/* 个人中心修改页面 */
function showCenterPage(){
  $("#userCenter").removeClass("display-none");
  $(".cover-div").removeClass("display-none");
  setTimeout(function(){
    $("#userCenter").addClass("show");
    $(".cover-div").addClass("show");
  },100);
}
function editInfo(){
  $(".common-input").removeAttr("disabled").addClass("bg-eee");
  $("#female").removeAttr("disabled");
  $("#male").removeAttr("disabled");
  $("#editBtn").addClass("display-none");
  $("#finishBtn").removeClass("display-none");
  $("#passWord").attr("type","text");
  bindDate("memberBir");
  $("#disabledDiv").addClass("display-none");
  playBtnClick();
}
function bindUserData(){
  var userData = postData("getUserData");
//  var userData = {"membertele":"18818227695","membersex":"男","answer":"萍","membername":"萍","memberaccount":"萍","question":"我是谁","memberbir":"1993","password":"1"};
  var $userCenter = $("#userCenterWrapper").find("input");
  $($userCenter[0]).val(userData.memberaccount);
  $($userCenter[1]).val(userData.password);
  $($userCenter[2]).val(userData.membername);
  $($userCenter[3]).val(userData.memberbir);
  if(userData.membersex=="女"){
    $("#female").attr("checked","checked");
  }else{
    $("#male").attr("checked","checked");
  }
  $($userCenter[7]).val(userData.membertele);
  $($userCenter[8]).val(userData.question);
  $($userCenter[9]).val(userData.answer);
}
function changeSex(sex){
  if(sex=="0"){
    $("#memberSex").val("女");
  }else{
    $("#memberSex").val("男");
  }
}
function updateUserData(){
  var data = getFormVal("userCenterForm");
  $(".common-input").attr("disabled","disabled").removeClass("bg-eee");
  $("#female").attr("disabled","disabled");
  $("#male").attr("disabled","disabled");
  $("#finishBtn").addClass("display-none");
  $("#editBtn").removeClass("display-none");
  $("#passWord").attr("type","passWord");
  $("#disabledDiv").removeClass("display-none");
  postData("updateUserData",data);
  closeCenterPage();
  playBtnClick();
}
/*  与后台的交互方法  */
function postData(method,data){
  if(!isTest){
    
    if(!data){
      return $.parseJSON(dataPost.dataPost(method,"")); 
    }else{
      return $.parseJSON(dataPost.dataPost(method,JSON.stringify(data))); 
    }
  }else{
    return [{}];
  }
}




















/*
function getFormVal(formId,method){
  var data=getElemsObj(formId,"data-name");
  data["method"] = method;
  return data;
}
function getElemsObj(formId,name){//取元素组成json
  var $inputs = $("#"+formId).find("["+name+"]");
  var data={};
  $.each($inputs,function(i,input){
    data[$(input).attr(name)] = $(input).val();
  });
  return data;
}*/
function validateForm(formId){
  var result=[];
  var isPass=true;
  var elems=getElemsStrs(formId,"data-valid");//这个属性值会重名，所以要用数组不用json
  $.each(elems,function(i,elem){
    result[i] = checkValue(elem[0],elem[1]);
  });
  var $inputs = $("#"+formId).find("[data-valid]");
  var color="";
  $.each($inputs,function(i,input){
    if(result[i]=="√通过"||result[i]==""){
      color="#0d0";
    }else{
      color="#f00";
      if(isPass)
        isPass=false;
        
      //$inputs.css()
    }
    $(input).next().css("color",color).text(result[i]);
  });
  if(!isPass){
    mesShow("输入有误！请按提示改正。","warn");
    return false;
  }
  return true;
}

function getElemsStrs(formId,name){//取元素组成数组
  var $inputs = $("#"+formId).find("["+name+"]");
  var data=[];
  $.each($inputs,function(i,input){
    data[i]=[$(input).attr(name),$(input).val()];
  });
  return data;
}

function checkValue(typeStr,value){
  var types=typeStr.split(",");//将类型值以逗号分隔成数组
  if(types.length==1){
    var reg = getRegExp(typeStr);
    if(!reg.test(value)){
      if(typeStr=="notnull")
        return "*必填";
      else
        return "*格式错误";
    }else{
      if(typeStr=="notnull")
        return "√通过";
      else{
        if(value=="")
          return "";
        else
          return "√通过";
      }
    }
  }else if(types.length==2){//参数有两个
    if(!value){//若为空直接返回
      return "*必填";
    }else{
      var type;
      if(types[0]=="notnull"){//找出不是notnull的那个参数
        type=types[1];
      }else{
        type=types[0];
      }
      var reg=getRegExp(type);
      if(reg=="")
        return "data-valid参数错误";
      else if(!reg.test(value)){
        return "*格式错误";
      }else{
        return "√通过";
      }
    }
  }else{
    return "data-valid参数个数错误";
  }
}
function getRegExp(type){
  switch(type){
    case "notnull":return /^\S+$/;break;
    case "date":return /^(([12]\d{3}-((0[1-9])|(1[1-2]))-((0[1-9])|([1-2]\d)|3(0|1)))|(\S{0}))$/;break;
    case "email":return /^((\w*@\w*.com)|(\S{0}))$/;break;
    case "number":return /^((\d+)|(\S{0}))$/;break;
    //待扩展
    case "express":return value;break;
    default:return "";break;
  }
}
function getUrlPara(){
  var urlPara=decodeURI(location.search.substring(1)).split("&");
  if(urlPara.length==0){
    return "";
  }else if(urlPara.length==1){
    return urlPara[0].split("=")[1];
  }else{
    var para={};
    for(var i=0;i<urlPara.length;i++){
      var paraEach=urlPara[i].split("=");
      para[paraEach[0]] = paraEach[1];
    }
    return para;
  }
}
function playBgm(){
  postData("playBgm");
  isPlayBgm=true;
}
function stopBgm(){
  postData("stopBgm");
  isPlayBgm=false;
}
function playBtnClick(){
  postData("playBtnClick");
}