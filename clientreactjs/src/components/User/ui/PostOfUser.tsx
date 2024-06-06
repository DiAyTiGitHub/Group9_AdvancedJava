import PostList, { LoadingPost } from "@/components/Post/PostList";
import Loader from "@/components/shared/Loader";
import NoData from "@/components/shared/NoData";
import { useGetDataPostByUserId } from "@/lib";
import { useStore } from "@/stores";
import { SearchObjectType } from "@/types";
import { useState } from "react";
import { useParams } from "react-router-dom";

const PostOfUser = () => {
  const { profileId } = useParams();

  const { postStore } = useStore();

  const [paging, setPaging] = useState<SearchObjectType>({
    pageIndex: 0,
    pageSize: 20,
    mileStoneId: "",
  });

  const { getPostOfUser } = postStore;
  const {
    ref,
    res: posts,
    isLoading,
    showLoadMore,
    endOfListRef,
  } = useGetDataPostByUserId({
    getRequest: getPostOfUser,
    paging: paging,
    setPaging: setPaging,
    userId: profileId,
  });
  return (
    <>
      {isLoading ? (
        <LoadingPost />
      ) : (
        <>
          {!posts || posts.length === 0 ? (
            <div className="bg-white w-full rounded-md h-full py-10 mt-4">
              <NoData title="Bạn chưa có bài viết nào, hãy kết nối với bạn bè để cùng chia sẻ những khoản khắc !!!" />
            </div>
          ) : (
            <>
              <PostList
                posts={posts}
                isLoading={isLoading}
                endOfListRef={endOfListRef}
                lastId={paging.mileStoneId}
              />
            </>
          )}
          {showLoadMore && (
            <div ref={ref}>
              <Loader />
            </div>
          )}
        </>
      )}
    </>
  );
};

export default PostOfUser;
