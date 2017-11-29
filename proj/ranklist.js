$(document).ready(function(){
	$(function(){
		$.ajax({
			type : "Post",
			url : "topfive.php",
			success : function(data){
				var obj = $.parseJSON(data);
				var temp;
				$.each(obj,function(){
					temp = "<tr>"
					temp = temp + "<td>";
					temp = temp + this['Name'];
					temp = temp + "</td>";
					temp = temp + "<td>";
					temp = temp + this['LeagueCode'];
					temp = temp + "</td>";
					temp = temp + "<td>";
					temp = temp + this['Rating'];
					temp = temp + "</td>";
					temp = temp + "</tr>"
					$('#ranktable > tbody:last-child').append(temp);
				});
			}
		});
	});
});