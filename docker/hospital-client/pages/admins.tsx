import { withAuth } from "@/components/Auth";
import Doctor_Admin_ProfileCard from "@/components/Doctor_Admin_ProfileCard";
import { getAdminPersonalDetails } from "@/redux/actions/adminActions";
import { useAppDispatch, useAppSelector } from "@/redux/store";
import { useEffect, useState } from "react";

// Profile Page for Admins
function AdminProfilePage() {
  const adminDetails = useAppSelector((state) => state.admin);
  const [adminDetail, setAdminDetail] = useState({
    username: "",
    speciality: "",
    hospitalId: "",
  });
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getAdminPersonalDetails());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    setAdminDetail({
      username: adminDetails.username,
      speciality: adminDetails.role,
      hospitalId: adminDetails.hospitalId,
    });
  }, [adminDetails]);
  return <Doctor_Admin_ProfileCard imgSrc="/Admin.avif" {...adminDetail} />;
}

export default withAuth(AdminProfilePage);
