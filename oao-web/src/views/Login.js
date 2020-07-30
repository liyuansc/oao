import React, {Component} from 'react';
import './Login.less';
import {Button, Checkbox, Form, Input} from 'antd';
import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {login} from '../api/uaa'

const onFinish = values => {
  login(values.username, values.password, values.rememberMe)

  console.log('Received values of form: ', values);
};

class App extends Component {
  constructor(props) {
    super(props)
    this.form = null
    this.state = {}
  }

  formRef = (element) => {
    this.form = element
  }

  login = async (values) => {
    console.log(values)

    login(values.username, values.password, values.remember).then(res => {
      console.log(res)
    }).catch(e => {
      console.log(e)
    })
    //
    //
    // try {
    //   let res = await login(values.username, values.password, values.rememberMe)
    //   alert(1)
    //   console.log(res)
    //   console.log(res.data)
    // } catch (e) {
    //   alert(2)
    //   console.log(e.message)
    // }
  }

  render() {
    return (
      <div id="components-form-normal-login">
        <h1>Login..</h1>
        <Form
          ref={this.formRef}
          name="normal_login"
          className="login-form"
          initialValues={{remember: true}}
          onFinish={this.login}
        >
          <Form.Item
            name="username"
            rules={[{required: true, message: '用户名'}]}
          >
            <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="用户名"/>
          </Form.Item>
          <Form.Item
            name="password"
            rules={[{required: true, message: '密码'}]}
          >
            <Input
              prefix={<LockOutlined className="site-form-item-icon"/>}
              type="password"
              placeholder="密码"
            />
          </Form.Item>
          <Form.Item>
            <Form.Item name="remember" valuePropName="checked" noStyle>
              <Checkbox>记住我</Checkbox>
            </Form.Item>

            <a className="login-form-forgot" href="">忘记密码</a>
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" className="login-form-button">登录</Button>
            或 <a href="">注册</a>
          </Form.Item>
        </Form>
      </div>
    );
  }
};

export default App;
