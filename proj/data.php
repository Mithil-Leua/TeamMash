<?php
#!/usr/bin/php

$servername = "localhost";
$username = "mithil";
$password = "kv23200266";
$dbname = "TEAMRATING";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT * FROM mytable ORDER BY RAND() LIMIT 2";
$result = $conn->query($sql);

$to_encode = array();

if($result->num_rows > 0){
	while($row = $result->fetch_assoc()){
		$to_encode[] = $row;
	}
	echo json_encode($to_encode);
}
else{
	echo "Error";
}

$conn->close();
?>