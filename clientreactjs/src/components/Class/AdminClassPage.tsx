import { Input } from "@mui/material";
import { Search } from "lucide-react";
import TableClass from "./TableClass";
import Pagination from "./Pagination";
import CreateClass from "./CreateClass";
import { useStore } from "@/stores";
import { useGetDataPagination } from "@/lib";
import { useState } from "react";
import { SearchObjectType } from "@/types";
import NoData from "../shared/NoData";
import TableSkeleton from "../skeleton/TableSkeleton";

const AdminClassPage = () => {
  const [paging, setPaging] = useState<SearchObjectType>({
    pageSize: 5,
    pageIndex: 1,
    keyWord: "",
  });
  const [searchValue, setSearchValue] = useState("");

  const handleChange = (event: any) => {
    setSearchValue(event.target.value);
  };

  const handleClick = () => {
    setPaging((prevPaging) => ({
      ...prevPaging,
      keyWord: searchValue,
    }));
    // Thực hiện các thao tác khác với giá trị searchValue ở đây
  };
  const { classStore } = useStore();
  const { pagingClass } = classStore;

  const {
    res: dataClass,
    isLoading,
    isLeftDisable,
    isRightDisable,
  } = useGetDataPagination({ getRequest: pagingClass, paging: paging });

  if (isLoading) return <TableSkeleton length={5} styles="" />;

  return (
    <div className="px-5 bg-blue-2 w-full mr-5 rounded-md">
      <div className="flex flex-col w-full">
        <div className="mt-5 w-full px-5">
          <h2 className="text-body-medium">Danh sách lớp học</h2>
          <div className="w-full flex justify-between mt-2">
            <div className="flex items-end">
              <Input
                type="search"
                className="px-5"
                placeholder="Tìm lớp học..."
                onChange={handleChange}
                disabled
              />
              <button className="bg-primary p-2" onClick={handleClick}>
                <Search color="#fff" />
              </button>
            </div>
            <div>
              <CreateClass />
            </div>
          </div>
        </div>

        {!dataClass || dataClass.length === 0 ? (
          <NoData title="Danh sách lớp học đang trống" />
        ) : (
          <>
            <div className="mt-10 px-10 bg-white shadow-lg py-10 rounded-sm">
              <TableClass classData={dataClass} isLoading={isLoading} />
            </div>

            <Pagination
              isLeftDisable={isLeftDisable}
              isRightDisable={isRightDisable}
              setPaging={setPaging}
            />
          </>
        )}
      </div>
    </div>
  );
};

export default AdminClassPage;
