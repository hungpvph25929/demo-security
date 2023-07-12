import { Button } from "antd";
import { useNavigate } from "react-router";
import { UserApi } from "../api/UserApi.api";

const Home = () => {
  const navigate = useNavigate();

  const logOut = () => {
    UserApi.logOut().then((response) => {
      localStorage.removeItem("userCurrent");
      navigate("/login");
    });
  };

  return (
    <div>
      <h1>Xin chào tôi là HOME</h1>
      <Button onClick={logOut}>Log out</Button>
    </div>
  );
};

export default Home;
