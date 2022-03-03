from flask import *
from database import *

from datetime import datetime

admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():
	return render_template('admin_home.html')


@admin.route('/admin_manage_floor',methods=['get','post'])
def admin_manage_floor():
	data={}
	q="select * from floor"
	res=select(q)
	data['floor']=res
	if 'action' in request.args:
		action=request.args['action']
		fid=request.args['fid']
			
	else:
		action=None
	if action=="update":
		q="select * from floor where floor_id='%s'"%(fid)
		res=select(q)
		data['floor_up']=res
		if 'ufloor' in request.form:
			floorname=request.form['floorname']
			floortype=request.form['floortype']
			q="update floor set floor_name='%s',floor_type='%s' where floor_id='%s'"%(floorname,floortype,fid)
			update(q)
			return redirect(url_for('admin.admin_manage_floor'))
	if action=="delete":
		q="delete from floor where floor_id='%s'"%(fid)
		delete(q)
		return redirect(url_for('admin.admin_manage_floor'))		
	if 'submit' in request.form:
		floorname=request.form['floorname']
		floortype=request.form['floortype']
	
		q="insert into floor values(null,'%s','%s')"%(floorname,floortype)
		insert(q)
		return redirect(url_for('admin.admin_manage_floor'))
	return render_template('admin_manage_floor.html',data=data)




@admin.route('/admin_manage_room',methods=['get','post'])
def admin_manage_room():
	data={}
 
	q="select * from floor"
	res1=select(q)
	data['floor']=res1
 
	q="select * from room inner join floor using(floor_id)"
	res=select(q)
	data['room']=res
	if 'action' in request.args:
		action=request.args['action']
		rid=request.args['rid']
	else:
		action=None
	if action=="update":
		q="select * from room innee join floor using(floor_id) where room_id='%s'"%(rid)
		res=select(q)
		data['room_up']=res
	if 'usubmit' in request.form:
		roomnumber=request.form['roomnumber']
		nopersons=request.form['nopersons']
		roomrent=request.form['roomrent']
		messrent=request.form['messrent']
		details=request.form['details']
		q="update room set no_persons='%s',room_number='%s',room_rent='%s',details='%s',mess_rent='%s' where room_id='%s'"%(nopersons,roomnumber,roomrent,details,messrent,rid)
		update(q)
		return redirect(url_for('admin.admin_manage_room'))
	if action=="delete":
		q="delete from room where room_id='%s'"%(rid)
		delete(q)
		return redirect(url_for('admin.admin_manage_room'))		
	if 'submit' in request.form:
		floor_id=request.form['floor_id']
		nopersons=request.form['nopersons'];
		roomnumber=request.form['roomnumber']
		roomrent=request.form['roomrent']
		messrent=request.form['messrent']
		details=request.form['details']
		q="insert into room values(null,'%s','%s','%s','%s','%s','%s','available')"%(floor_id,nopersons,roomnumber,roomrent,messrent,details)
		insert(q)
		return redirect(url_for('admin.admin_manage_room'))
	return render_template('admin_manage_room.html',data=data)

@admin.route('/admin_manage_students',methods=['get','post'])
def admin_manage_students():
	data={}
	floor_id=request.args['floor_id']
 
	q="SELECT * FROM room INNER JOIN `floor` USING(`floor_id`)  WHERE `floor_id`='%s'"%(floor_id)
	res=select(q)
	data['room']=res
	q="SELECT * FROM student INNER JOIN room USING(room_id) WHERE `floor_id`='%s'"%(floor_id)
	res=select(q)
	data['student']=res
	if 'action' in request.args:
		action=request.args['action']
		sid=request.args['sid']
			
	else:
		action=None
	if action=="update":
		q="select * from student where student_id='%s'"%(sid)
		res=select(q)
		data['s_up']=res
		if 'submit' in request.form:
			roomnumber=request.form['roomnumber']
			firstname=request.form['firstname']
			lastname=request.form['lastname']
			gender=request.form['gender']
			phone=request.form['phone']
			email=request.form['email']
			place=request.form['place']
			district=request.form['district']
			adhar=request.form['adhar']
			q="update student set room_id='%s',firstname='%s',lastname='%s',gender='%s',phone='%s',email='%s',place='%s',district='%s',adhar_no='%s' where student_id='%s'"%(roomnumber,firstname,lastname,gender,phone,email,place,district,adhar,sid)
			update(q)
			return redirect(url_for('admin.admin_manage_students',floor_id=floor_id))
	if action=="delete":
		q="delete from student where student_id='%s'"%(sid)
		delete(q)
		return redirect(url_for('admin.admin_manage_students',floor_id=floor_id))
			
	if 'submit' in request.form:
		roomnumber=request.form['roomnumber']
		firstname=request.form['firstname']
		lastname=request.form['lastname']
		gender=request.form['gender']
		phone=request.form['phone']
		email=request.form['email']
		place=request.form['place']
		district=request.form['district']
		adhar=request.form['adhar']
		username=request.form['username']
		password=request.form['password']
		q="SELECT *,COUNT(`room_id`) AS croom FROM `student` WHERE `room_id`='%s'"%(roomnumber)
		rd=select(q)
		if rd:
			croom=rd[0]['croom']
			print("croom",croom)
			q1="SELECT * FROM `room` WHERE `room_id`='%s'"%(roomnumber)
			rd1=select(q1)
			no_p=rd1[0]['no_persons']
			print("no_p",no_p)
			if int(no_p)>int(croom):
				q="SELECT * FROM `login` INNER JOIN `student` USING(`login_id`) WHERE `username`='%s' OR `phone`='%s' OR `email`='%s' OR `adhar_no`='%s'"%(username,phone,email,adhar)
				rr=select(q)
				if rr:
					flash("Username/Phone/Email Or Adhar Number Already Exist. Please Choose Anothe Details.")
				else:
					q="insert into login values(null,'%s','%s','student')"%(username,password)
					id=insert(q)
					z="insert into student values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,roomnumber,firstname,lastname,gender,phone,email,place,district,adhar)
					insert(z)
					return redirect(url_for('admin.admin_manage_students',floor_id=floor_id))
			else:
				flash("Sorry. Room is Full. Please Choose Another Room")
				return redirect(url_for('admin.admin_add_student_to_room'))

	return render_template('admin_manage_students.html',data=data)

@admin.route('/admin_view_leaverequest',methods=['get','post'])
def admin_view_leaverequest():
	data={}
	q="SELECT * FROM request INNER JOIN `student` USING(`student_id`)"
	res=select(q)
	data['request']=res
	if 'action' in request.args:
		action=request.args['action']
		rid=request.args['rid']
			
	else:
		action=None
	if action=='accept':
		q="update request set status='accept' where request_id='%s'"%(rid)
		update(q)
		return redirect(url_for('admin.admin_view_leaverequest'))
	if action=='reject':
		q="update request set status='reject' where request_id='%s'"%(rid)
		update(q)
		return redirect(url_for('admin.admin_view_leaverequest'))		
	return render_template('admin_view_leaverequest.html',data=data)

@admin.route('/admin_add_bill',methods=['get','post'])
def admin_add_bill():
	data={}
	sid=request.args['sid']
	room_rent=request.args['room_rent']
	data['room_rent']=room_rent
	mess_rent=request.args['mess_rent']
	data['mess_rent']=mess_rent
 
	if 'submit' in request.form:
		amounts=request.form['amount']
		amount=round(float(amounts),2)
  
		nol=request.form['nol']
		nod=request.form['nod']
  
		proom_rents=request.form['proom_rent']
		proom_rent=round(float(proom_rents),2)

		pmess_rents=request.form['pmess_rent']
		pmess_rent=round(float(pmess_rents),2)

		q="insert into bill values(null,'%s',curdate(),'%s','%s','%s','%s','%s','pending')"%(sid,nol,nod,proom_rent,pmess_rent,amount)
		insert(q)
		return redirect(url_for('admin.admin_add_bill',sid=sid,room_rent=room_rent,mess_rent=mess_rent))
	now=datetime.now()
	dates="%"+now.strftime('%m-%Y')
	q="select count(request_id) as cc from request where student_id='%s' and leavedate like'%s'" %(sid,dates)
	res=select(q)
	print(q)
	data['attendnace']=res[0]['cc']
	return render_template('admin_manage_bill.html',data=data)

@admin.route('/admin_view_bill')
def admin_view_bill():
	data={}
	firstname=request.args['firstname']
	data['firstname']=firstname
	lastname=request.args['lastname']
	data['lastname']=lastname
	sid=request.args['sid']
	q="select * from bill inner join student using(student_id) where student_id='%s'"%(sid)
	res=select(q)
	data['bill']=res
	return render_template('admin_view_bill.html',data=data)	


@admin.route('/admin_add_student_to_room',methods=['get','post'])
def admin_add_student_to_room():
	data={}
	q="SELECT * FROM `floor`"
	res=select(q)
	data['floor']=res
	if 'next' in request.form:
		floor_id=request.form['floor_id']
		return redirect(url_for("admin.admin_manage_students",floor_id=floor_id))
	return render_template('admin_add_student_to_room.html',data=data)	


@admin.route('/admin_view_vaccancy')
def admin_view_vaccancy():
	data={}
	rid=request.args['rid']
	no_persons=request.args['no']

	q="select * from student where room_id='%s'"%(rid)
	res=select(q)
	print(len(res))
	data['room']=res
	print(res)
	data['vac']=int(no_persons)-int(len(res))
	print(data['vac'])
	return render_template('admin_view_vaccancy.html',data=data)