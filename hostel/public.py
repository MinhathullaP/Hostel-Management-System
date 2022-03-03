from flask import *
from database import *
public=Blueprint('public',__name__)

@public.route('/')
def home():
	return render_template('index.html')

@public.route('/login',methods=['get','post'])
def login():
	if 'submit' in request.form:
		username=request.form['username']
		password=request.form['password']
		q="select * from login where username='%s' and password='%s'"%(username,password)
		res=select(q)
		if res:
			session['lid']=res[0]['login_id']
			if res[0]['usertype']=="admin":
				z="select * from login where login_id='%s'"%(session['lid'])
				a=select(z)
				if a:
					session['login_id']=a[0]['login_id']
					return redirect(url_for('admin.admin_home'))
			# elif res[0]['usertype']=="shop":
			# 	z="select * from shops where login_id='%s'"%(session['lid'])
			# 	a=select(z)
			# 	if a:
			# 		session['shop_id']=a[0]['shop_id']
			# 		return redirect(url_for('shop.shop_home'))
			# elif res[0]['usertype']=="user":
			# 	z="select * from users where login_id='%s'"%(session['lid'])
			# 	a=select(z)
			# 	if a:
			# 		session['user_id']=a[0]['user_id']
			# 		return redirect(url_for('user.user_home'))				
	return render_template('login.html')
@public.route('/AboutUs')
def AboutUs():
	return render_template('AboutUs.html')
