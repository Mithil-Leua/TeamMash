$(document).ready(function(){
	$("#ajaxButton").click(function(){

		$("#selectButton1").show();
		$("#selectButton2").show();

		$.ajax({
			type : "Post",
			url : "data.php",
			success : function(data){
				var obj = $.parseJSON(data);
				var teamName1,teamLeague1,teamName2,teamLeague2;
				teamName1 = teamName2 = teamLeague1 = teamLeague2 = ""
				var i = 0;
				$.each(obj,function(){
					if(i == 0){
						teamName1 = teamName1 + this['Name'];
						teamLeague1 = teamLeague1 + this['LeagueCode'];
					// result = result + this['Name'] + " " + this['LeagueCode'];
					}
					if(i == 1){
						teamName2 = teamName2 + this['Name'];
						teamLeague2 = teamLeague2 + this['LeagueCode'];
					// result = result + this['Name'] + " " + this['LeagueCode'];
					}
					i++;
				});
				if(i == 0){
					alert("Couldn't load teams. Try Again");
				}
				// teamName1 = teamName1 + "</p>"
				// teamName2 = teamName2 + "</p>"
				// teamLeague1 = teamLeague1 + "</p>"
				// teamLeague2 = teamLeague2 + "</p>"
				$("#team1").html(teamName1);
				$("#team2").html(teamName2);
				$("#league1").html(teamLeague1);
				$('#league2').html(teamLeague2);
			}
		});
	});
});


$(document).ready(function(){
	$("#getTopFive").click(function(){

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
					$("#ranktable").html(temp);
				});
			}
		});
	});
});