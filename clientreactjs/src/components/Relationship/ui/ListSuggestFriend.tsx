import NoData from "@/components/shared/NoData";
import { IUser } from "@/types";
import CustomButtonFriend from "../CustomButtonFriend";
import { useStore } from "@/stores";
import MutualFriends from "@/components/User/ui/MutualFriend";
import { Grid } from "@mui/material";

type Props = {
  suggestFriends: any;
};
const ListSuggestFriend = ({ suggestFriends }: Props) => {
  const { relationshipStore } = useStore();

  const { addFriend } = relationshipStore;
  return (
    <>
      {
        (!suggestFriends || suggestFriends.length === 0) &&
        <NoData title="Chưa có bạn bè gợi ý" style="h-[100px] w-[100px]" />
      }

      <Grid container spacing={2}>

        {suggestFriends?.map((friend: IUser) => (
          <Grid
            item
            xs={12}
            sm={4}
            lg={3}
            key={friend?.id}
          >
            <div className="friendCard cursor-pointer pb-2 br-6">
              <div className="flex flex-col gap-3 justify-between flex-1 ">
                <div
                  className="rounded-xl space-y-2 overflow-hidden"
                  onClick={() =>
                    (window.location.href = `/profile/${friend?.id}`)
                  }
                >
                  <img
                    src={friend?.avatar || "/person.jpg"}
                    alt="avatar"
                    className="object-cover friendAvatar"
                  />
                  <p
                    className=" font-bold px-5"
                    onClick={() =>
                      (window.location.href = `/profile/${friend?.id}`)
                    }
                  >
                    {friend?.lastName} {friend?.firstName}
                  </p>
                </div>

                <div className="px-5">
                  {friend?.mutualFriends?.length > 0 && (
                    <MutualFriends mutualFriends={friend?.mutualFriends} />
                  )}
                </div>

                <div className="w-full px-5 ">
                  <CustomButtonFriend
                    icon="UserPlus"
                    handleFn={addFriend}
                    title="Thêm bạn bè"
                    message="Đã gửi lời mời"
                    id={friend.id}
                  />
                </div>
              </div>
            </div>
          </Grid>
        ))}


      </Grid>

    </>
  )
};

export default ListSuggestFriend;
