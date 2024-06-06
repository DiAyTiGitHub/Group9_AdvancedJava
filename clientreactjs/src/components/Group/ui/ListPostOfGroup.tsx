import PostList from "@/components/Post/PostList";
import NoData from "@/components/shared/NoData";

type Props = {
  posts: any;
};
const ListPostOfGroup = ({ posts }: Props) => {
  return (
    <>
      {!posts || posts.length === 0 ? (
        <NoData
          title="Chưa có bài viết nào trong nhóm"
          style="h-[100px] w-[100px]"
        />
      ) : (
        <>
          <PostList posts={posts} />
        </>
      )}
    </>
  );
};

export default ListPostOfGroup;
