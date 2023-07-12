import React from "react";
import { Form, Input, Button, DatePicker, Select } from "antd";
import {
  UserOutlined,
  MailOutlined,
  LockOutlined,
  PhoneOutlined,
} from "@ant-design/icons";
import { UserApi } from "../api/UserApi.api";
import { useNavigate } from "react-router";

const { Option } = Select;

const SignUp = () => {
  const navigate = useNavigate();

  const onFinish = (values) => {
    console.log(values);
    UserApi.register(values).then((response) => {
      alert("Đăng ký thành công");
      navigate("/login");
    });
  };

  return (
    <div>
      <h1>Đăng ký</h1>
      <Form name="signup" onFinish={onFinish}>
        <Form.Item
          name="name"
          rules={[
            { required: true, message: "Name can't empty" },
            { max: 50, message: "Name should not exceed 50 characters" },
          ]}
        >
          <Input prefix={<UserOutlined />} placeholder="Họ và tên" />
        </Form.Item>
        <Form.Item
          name="email"
          rules={[
            { required: true, message: "Email can't empty" },
            { type: "email", message: "Email is wrong format" },
            { max: 100, message: "Email should not exceed 100 characters" },
          ]}
        >
          <Input prefix={<MailOutlined />} placeholder="Email" />
        </Form.Item>
        <Form.Item
          name="birthDate"
          rules={[{ required: true, message: "Date of birth can't empty" }]}
        >
          <DatePicker placeholder="Date of birth" />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            { required: true, message: "Password can't empty" },
            { max: 30, message: "Password should not exceed 30 characters" },
          ]}
        >
          <Input.Password
            prefix={<LockOutlined />}
            placeholder="Enter password"
          />
        </Form.Item>
        <Form.Item
          name="phoneNumber"
          rules={[
            { required: true, message: "Phone number can't empty" },
            {
              max: 20,
              message: "Phone number should not exceed 20 characters",
            },
            {
              pattern: /^(\+84|0)\d{9,10}$/,
              message: "Phone number is wrong format",
            },
          ]}
        >
          <Input prefix={<PhoneOutlined />} placeholder="Enter Phone number" />
        </Form.Item>
        <Form.Item
          name="gender"
          rules={[{ required: true, message: "Choose your gender" }]}
        >
          <Select placeholder="Gender">
            <Option value={true}>Male</Option>
            <Option value={false}>Female</Option>
          </Select>
        </Form.Item>
        <Form.Item
          name="role"
          rules={[{ required: true, message: "Choose a role" }]}
        >
          <Select placeholder="Role">
            <Option value="MENTOR">Mentor</Option>
            <Option value="INTERN">Intern</Option>
          </Select>
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Đăng ký
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default SignUp;
