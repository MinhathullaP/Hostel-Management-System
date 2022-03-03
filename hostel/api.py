from flask import *
from database import *
import demjson

api=Blueprint('api',__name__)

@api.route('/login')
def login():
	data={}
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(username,password)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return demjson.encode(data)

@api.route('/student_view_bill')
def student_view_bill(): 
	data={}
	lid=request.args['lid']
	# q="select * from bill where student_id=(select student_id from student where login_id='%s') "%(lid)
	q="SELECT *,`bill`.`status` AS bstatus,CONCAT(`student`.`firstname`,' ',`student`.`lastname`) AS sname FROM bill INNER JOIN `student` USING(`student_id`) INNER JOIN `room` USING(room_id) INNER JOIN `floor` USING(floor_id) where student_id=(select student_id from student where login_id='%s') "%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
		
	return demjson.encode(data)

@api.route('/student_make_payment',methods=['get','post'])
def student_make_payment():
	data={}
	lid=request.args['lid']
	amount=request.args['amount']
	bid=request.args['bid']
	q="insert into payment values(null,'%s','%s',curdate())"%(bid,amount)
	insert(q)
	z="update bill set status='paid' where bill_id='%s'"%(bid)
	update(z)
	data['status']="success"
	return demjson.encode(data)	

@api.route('/student_apply_leave',methods=['get','post'])
def student_apply_leave():
	data={}
	lid=request.args['lid']
	reason=request.args['reason']
	date=request.args['date']
	z="select * from request where student_id=(select student_id from student where login_id='%s') and leavedate='%s'"%(lid,date)
	a=select(z)
	if a:
		data['status']="duplicate"
	else:	
		q="insert into request values(null,(select student_id from student where login_id='%s'),'%s','%s',curdate(),'pending')"%(lid,date,reason)
		insert(q)
		print(q)
		data['status']="success"
	return demjson.encode(data)

@api.route('/student_view_leave_request')
def student_view_leave_request():
	data={}
	lid=request.args['lid']
	q="select * from request where student_id=(select student_id from student where login_id='%s')"%(lid)
	res=select(q)
	print(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
		
	return demjson.encode(data)	


@api.route('/Change_password')
def Change_password():
	data={}
	lid=request.args['lid']
	newpass=request.args['newpass']
	confirm=request.args['confirm']
	q="SELECT * FROM `login` WHERE `login_id`='%s'"%(lid)
	res=select(q)
	
	if res:
		if newpass==confirm:
			q="UPDATE `login` SET `password`='%s' WHERE `login_id`='%s'"%(confirm,lid)
			update(q)
			data['status']="success"
			data['data']=res
		else:
			data['status']="failed"
			
	return demjson.encode(data)	


# @api.route('/View_bill')
# def View_bill():
# 	data={}
# 	bids=request.args['bids']
	
# 	q="SELECT * FROM `login` WHERE `login_id`='%s'"%(lid)
# 	res=select(q)
	
# 	if res:
# 		data['status']="success"
# 		data['data']=res
# 	else:
# 		data['status']="failed"
			
# 	return demjson.encode(data)	