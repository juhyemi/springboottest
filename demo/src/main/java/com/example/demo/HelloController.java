package com.example.demo;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
//@RestController-java 객체를 json형태로 바꿔준다. 
//기본포트번호 8080
//오라클 express 버전 설치하면 포트번호 충돌
@CrossOrigin("*")
@RestController
@RequestMapping(value="/")
public class HelloController {
	@GetMapping("/getUserInfo")
	public HashMap<String, String> getUserInfo(){
		HashMap<String,String> map = new HashMap<>();
		//컬렉션클래스, 배열, Map, json, SortedList ....
		//배열의 요소는 index를 통해 읽고 쓸 수 있다.
		//HashMap, Dictionary, json 동일한 구조
		//키와 값 쌍으로 구성되ㅡㄴ 데이터를 저장해서 데이터를 읽고 쓸 때 키 값 찾아서 읽고 쓰기 한다.
		//{name:"홍길동",~~~} json과 같음
		map.put("name", "홍길동");
		map.put("phone", "010-0000-0000");
		map.put("address", "서울시 관악구");
		return map;
	}
	//정보를 두고 받는 방식 - get방식
	// /getUserInfo?userid=test&username=홍길동 => key, value 를 &로 연결
	// get의 새로운 방식
	// /getUserInfo/test
	
	//post방식은 => form 태그에 method="POST"로 바꿔야 한다.
	
	//add1?x=5&y=7 {x:5, y:7, result:12} - get
	//add2/5/7 {x:5, y:7, result:12} - get
	//add3 {x:5, y:7, result:12} - post
	
	@GetMapping("/add1")
	public HashMap<String, Object> add1(HttpServletRequest request, @RequestParam("x") int x, @RequestParam("y") int y){
		//HttpServletRequest 객체에 담아온다.
		//그래서 request.getparameter로 객체에서 원하는 값을 추출해야한다. 
		//spring은 이 과정을 하지 않아도 자동으로 정보값과 자료형을 맞춰서 준다.
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("x", x);
		map.put("y", y);
		map.put("result", x+y);
		return map;
	}
	
	@GetMapping("/add2/{x}/{y}")
	public HashMap<String, Object> add2(HttpServletRequest request, @PathVariable("x") int x, @PathVariable("y") int y){
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("x", x);
		map.put("y", y);
		map.put("result", x+y);
		return map;
	}

	
	@PostMapping("/add3")
	public HashMap<String, Object> add3(HttpServletRequest request, @RequestParam("x") int x, @RequestParam("y") int y){
		HashMap<String, Object> map = new HashMap<>();
		map.put("x", x);
		map.put("y", y);
		map.put("result", x+y);
		return map;
	}
	
	//@RequestBody - json으로 받아라 => map이나 dto로 받아야함
	@PostMapping("/add4")
	public HashMap<String, Object> add4(
			@RequestBody HashMap<String, Object> map){
		HashMap<String, Object> resultmap = new HashMap<>();
		int x = Integer.parseInt(map.get("x").toString());
		int y = Integer.parseInt(map.get("y").toString());
		//object는 바로 int로 받지 못해서 string 변경 후 int로 변환
		resultmap.put("x", x);
		resultmap.put("y", y);
		resultmap.put("result", x+y);
		return resultmap;
	}
	@PostMapping("/payment")
	public Integer payment(HttpServletRequest request, @RequestParam("hour") int hour, @RequestParam("perpay") int perpay){
		int totalMoney = hour*perpay;
		return totalMoney;
	}
	// /payment는 내가 /getPayment는 해설
	//@RequestBody : 데이터를 client가 json형태로 보낼때 json데이터를 받아서 자바객체로 전환과정을 거친다.
	// HashMap이나 DTO(Data Transfer Object)클래스를 만드는 것이 좋다.
	// DB 테이블 필드와 거의 1:1
	// 3개의 테이블을 조인해서 필요한 필드만큼 만들 수 있다.
	// 클라이언트로 부터 파라미터(정보)를 받아올 때 보통 DTO를 사용한다.
	// name=홍길동&age=12 ===> {"name":"홍길동","age":12}
	//Restful API - 데이터 주고받을 때 표준이 xml이나 json이다.
	//xml은 실제 데이터를 가져오는 parsing 과정이 별도로 필요하다.
	//(파서프로그램도 많다. 점점 시장에서 자리 잃고 있다. json으로 거의 통일되고 있는 상황
	
	@PostMapping("/getPayment")
	HashMap<String, Object> getPayment(@RequestBody HashMap<String, String> param){
		int work_time = Integer.parseInt(param.get("work_time"));
		int per_pay = Integer.parseInt(param.get("per_pay"));
		String name = param.get("name");
		
		int pay = work_time*per_pay;
		HashMap<String, Object> resultMap = new HashMap<>(); 
		resultMap.put("result", "OK");
		resultMap.put("msg", String.format("%s 님의 주급은 %d 입니다.", name, pay));
		
		return resultMap; 
	}
	
}
