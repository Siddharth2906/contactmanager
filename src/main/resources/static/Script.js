const togglesidebar=()=>{
    if($(".sidebar").is(":visible")){
       $(".sidebar").css("display","none")
       $(".main-content").css("margin","0%")
    }
    else{
        $(".sidebar").css("display","flex")
        $(".main-content").css("margin","20%")
    }
}