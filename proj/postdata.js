$(document).ready(function(){
	$("#selectButton1").click(function(){

		var Winner = document.getElementById("team1").innerText;
		var LeagueOfWinner  = document.getElementById("league1").innerText;

		var Loser = document.getElementById("team2").innerText;
		var LeagueOfLoser = document.getElementById("league2").innerText;

		var jsondata = {"WinnerTeam" : Winner , "LeagueOfWinner" : LeagueOfWinner, "LoserTeam" : Loser , "LeagueOfLoser" : LeagueOfLoser}

		$.ajax({
			type : "Post",
			url : "http://10.22.17.233:5050/postrequest",
			crossDomain: true,
			data : JSON.stringify(jsondata),
			dataType : "text",
			success : function(data){
				alert("You picked " + Winner);
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
			},
			error : function(data){
				alert("Couldn't post your data!");
			}
		});
	});
});


$(document).ready(function(){
	$("#selectButton2").click(function(){
		// var jsondata = {"Team" : "Real Madrid" , "League" : "La Liga"}
		
		var Winner = document.getElementById("team2").innerText;
		var LeagueOfWinner  = document.getElementById("league2").innerText;

		var Loser = document.getElementById("team1").innerText;
		var LeagueOfLoser = document.getElementById("league1").innerText;

		var jsondata = {"WinnerTeam" : Winner , "LeagueOfWinner" : LeagueOfWinner, "LoserTeam" : Loser , "LeagueOfLoser" : LeagueOfLoser}

		$.ajax({
			type : "Post",
			url : "http://10.22.17.233:5050/postrequest",
			crossDomain: true,
			data : JSON.stringify(jsondata),
			dataType : "text",
			success : function(data){
				alert("You picked " + Winner);

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
			},
			error : function(data){
				alert("Couldn't post your data!");
			}
		});
	});
});
